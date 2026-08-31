package com.example.note2snap.utils

import android.graphics.Rect
import com.example.note2snap.model.BlockType
import com.example.note2snap.model.NoteBlock
import com.example.note2snap.model.StructuredNote
import com.google.mlkit.vision.text.Text

object WhiteboardRuleEngine {

    private data class SpatialCell(
        val text: String,
        val box: Rect
    )

    private data class TableRow(
        val cells: MutableList<SpatialCell> = mutableListOf(),
        var top: Int = 0,
        var bottom: Int = 0
    )

    /**
     * Primary entry point using ML Kit Vision Text (enables spatial table recognition)
     */
    fun process(visionText: Text): StructuredNote {
        val lines = visionText.textBlocks.flatMap { it.lines }
        if (lines.isEmpty()) return StructuredNote("Untitled Scan", emptyList())

        // 1. Extract all line elements with bounding boxes
        val items = lines.mapNotNull { line ->
            line.boundingBox?.let { SpatialCell(line.text.trim(), it) }
        }.sortedBy { it.box.top }

        if (items.isEmpty()) return StructuredNote("Untitled Scan", emptyList())

        // 2. Extract Title (First non-empty line)
        val rawTitle = items.firstOrNull { it.text.isNotBlank() }?.text
            ?.replace(Regex("^[#*\\-]+\\s*"), "")
            ?: "Untitled Scan"

        // 3. Cluster text lines vertically into visual rows
        val rows = clusterIntoRows(items)

        // 4. Partition rows into Table Blocks vs. Line Blocks
        val blocks = mutableListOf<NoteBlock>()
        var i = 0

        while (i < rows.size) {
            val row = rows[i]
            val rowText = row.cells.joinToString(" ") { it.text }

            // Skip title row duplicate
            if (i == 0 && rowText.equals(rawTitle, ignoreCase = true)) {
                i++
                continue
            }

            // Check if this row starts a multi-column table section
            if (isTableRowCandidate(row)) {
                val tableRows = mutableListOf<TableRow>()
                while (i < rows.size && isTableRowCandidate(rows[i])) {
                    tableRows.add(rows[i])
                    i++
                }

                if (tableRows.size >= 2) {
                    val htmlTable = buildHtmlTableFromRows(tableRows)
                    val rawTableText = tableRows.joinToString("\n") { r -> r.cells.joinToString(" | ") { it.text } }
                    blocks.add(NoteBlock(rawTableText, BlockType.REGULAR_TEXT, htmlTable))
                    continue
                } else {
                    // Fallback single line if not enough rows for a full table
                    i -= tableRows.size
                }
            }

            // Rule-based single line processing
            val lineText = rowText.trim()
            if (lineText.isNotEmpty()) {
                blocks.add(parseSingleLineBlock(lineText))
            }
            i++
        }

        return StructuredNote(rawTitle, blocks)
    }

    /**
     * Fallback entry point using raw strings
     */
    fun process(rawLines: List<String>): StructuredNote {
        if (rawLines.isEmpty()) return StructuredNote("Untitled Scan", emptyList())

        val title = rawLines.firstOrNull { it.isNotBlank() }
            ?.replace(Regex("^[#*\\-]+\\s*"), "")
            ?: "Untitled Scan"

        val blocks = rawLines.drop(1)
            .filter { it.isNotBlank() }
            .map { parseSingleLineBlock(it.trim()) }

        return StructuredNote(title, blocks)
    }

    // --- SPATIAL CLUSTERING & TABLE GENERATION ---

    private fun clusterIntoRows(items: List<SpatialCell>): List<TableRow> {
        val rows = mutableListOf<TableRow>()
        for (item in items) {
            val matchingRow = rows.find { row ->
                val overlap = Math.min(row.bottom, item.box.bottom) - Math.max(row.top, item.box.top)
                overlap > (item.box.height() * 0.35)
            }

            if (matchingRow != null) {
                matchingRow.cells.add(item)
                matchingRow.top = Math.min(matchingRow.top, item.box.top)
                matchingRow.bottom = Math.max(matchingRow.bottom, item.box.bottom)
            } else {
                rows.add(TableRow(mutableListOf(item), item.box.top, item.box.bottom))
            }
        }
        rows.forEach { row -> row.cells.sortBy { it.box.left } }
        return rows
    }

    private fun isTableRowCandidate(row: TableRow): Boolean {
        if (row.cells.size >= 2) return true
        val text = row.cells.firstOrNull()?.text ?: return false
        return text.matches(Regex("^\\d{1,2}:\\d{2}\\s*(AM|PM|NN)?.*", RegexOption.IGNORE_CASE)) ||
                text.matches(Regex("^(oras|laban|lobby|time|match|vs|location|room|status)\\b.*", RegexOption.IGNORE_CASE))
    }

    private fun buildHtmlTableFromRows(rows: List<TableRow>): String {
        val allCells = rows.flatMap { it.cells }
        val columnBounds = detectColumnBounds(allCells)

        val sb = StringBuilder()
        sb.append("<table border='1' style='width:100%; border-collapse:collapse; margin:8px 0;'>")

        rows.forEachIndexed { rowIndex, row ->
            sb.append("<tr>")
            val isHeader = rowIndex == 0
            val rowGrid = Array(columnBounds.size) { StringBuilder() }

            for (cell in row.cells) {
                val colIdx = getBestColumnIndex(cell.box, columnBounds)
                if (rowGrid[colIdx].isNotEmpty()) rowGrid[colIdx].append(" ")
                rowGrid[colIdx].append(cell.text)
            }

            for (cellText in rowGrid) {
                val text = cellText.toString().trim().ifEmpty { "-" }
                val tag = if (isHeader) "th" else "td"
                val style = if (isHeader)
                    "style='background-color:#F2F2F7; padding:8px; font-weight:bold; text-align:left; color:#000000;'"
                else
                    "style='padding:6px; color:#000000;'"

                sb.append("<$tag $style>$text</$tag>")
            }
            sb.append("</tr>")
        }

        sb.append("</table>")
        return sb.toString()
    }

    private fun detectColumnBounds(cells: List<SpatialCell>): List<Pair<Int, Int>> {
        val sortedLefts = cells.map { it.box.left }.sorted()
        val clusters = mutableListOf<MutableList<Int>>()

        for (left in sortedLefts) {
            val cluster = clusters.find { c -> Math.abs(c.average() - left) < 140 }
            if (cluster != null) {
                cluster.add(left)
            } else {
                clusters.add(mutableListOf(left))
            }
        }

        return clusters.map { cluster ->
            val min = cluster.minOrNull() ?: 0
            val max = cluster.maxOrNull() ?: 0
            Pair(min - 20, max + 120)
        }.sortedBy { it.first }
    }

    private fun getBestColumnIndex(box: Rect, columns: List<Pair<Int, Int>>): Int {
        val center = box.centerX()
        for ((index, col) in columns.withIndex()) {
            if (center in col.first..col.second) return index
        }
        return columns.indices.minByOrNull { Math.abs(columns[it].first - box.left) } ?: 0
    }

    // --- SINGLE LINE RULE EVALUATOR ---

    private fun parseSingleLineBlock(trimmed: String): NoteBlock {
        return when {
            isBulletRule(trimmed) -> {
                val cleanText = trimmed.replace(Regex("^([\\-*•]|\\d+[.)])\\s*"), "")
                NoteBlock(trimmed, BlockType.BULLET_ITEM, "• $cleanText")
            }
            isKeyDefinitionRule(trimmed) -> {
                val parts = trimmed.split(":", limit = 2)
                val formatted = "<b>${parts[0].trim()}</b>: ${parts[1].trim()}"
                NoteBlock(trimmed, BlockType.KEY_DEFINITION, formatted)
            }
            isSectionHeaderRule(trimmed) -> {
                NoteBlock(trimmed, BlockType.SECTION_HEADER, "<b><font color='#000000'>${trimmed.uppercase()}</font></b>")
            }
            isMathRule(trimmed) -> {
                NoteBlock(trimmed, BlockType.MATHEMATICAL, "<i>[Formula]</i> $trimmed")
            }
            else -> NoteBlock(trimmed, BlockType.REGULAR_TEXT, trimmed)
        }
    }

    private fun isBulletRule(text: String): Boolean =
        text.matches(Regex("^([\\-*•]|\\d+[.)])\\s+.*"))

    private fun isKeyDefinitionRule(text: String): Boolean =
        text.contains(":") && !text.startsWith("http") && text.indexOf(":") in 2..25

    private fun isSectionHeaderRule(text: String): Boolean =
        (text.length in 3..30 && text == text.uppercase() && text.any { it.isLetter() }) || text.startsWith("##")

    private fun isMathRule(text: String): Boolean =
        text.contains(Regex("[=≠≈±<>]")) && text.any { it.isDigit() }
}