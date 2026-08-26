package com.example.note2snap.utils

import com.example.note2snap.model.BlockType
import com.example.note2snap.model.NoteBlock
import com.example.note2snap.model.StructuredNote

object WhiteboardRuleEngine {

    fun process(rawLines: List<String>): StructuredNote {
        if (rawLines.isEmpty()) return StructuredNote("Untitled Scan", emptyList())

        // Rule 1: First non-empty line becomes the Title
        val title = rawLines.firstOrNull { it.isNotBlank() }
            ?.replace(Regex("^[#*\\-]+\\s*"), "")
            ?: "Untitled Scan"

        val blocks = mutableListOf<NoteBlock>()

        for ((index, line) in rawLines.withIndex()) {
            val trimmed = line.trim()
            if (trimmed.isEmpty() || (index == 0 && trimmed == title)) continue

            val block = when {
                // Rule 2: Bullet points or numbered lists
                isBulletRule(trimmed) -> {
                    val cleanText = trimmed.replace(Regex("^([\\-*•]|\\d+[.)])\\s*"), "")
                    NoteBlock(trimmed, BlockType.BULLET_ITEM, "• $cleanText")
                }

                // Rule 3: Key-Value / Definition pair (e.g., "Term: Definition")
                isKeyDefinitionRule(trimmed) -> {
                    val parts = trimmed.split(":", limit = 2)
                    val formatted = "**${parts[0].trim()}**: ${parts[1].trim()}"
                    NoteBlock(trimmed, BlockType.KEY_DEFINITION, formatted)
                }

                // Rule 4: Section Headers (ALL CAPS text)
                isSectionHeaderRule(trimmed) -> {
                    NoteBlock(trimmed, BlockType.SECTION_HEADER, trimmed.uppercase())
                }

                // Rule 5: Mathematical equations or formulas
                isMathRule(trimmed) -> {
                    NoteBlock(trimmed, BlockType.MATHEMATICAL, "[Formula] $trimmed")
                }

                // Rule 6: Fallback Default
                else -> NoteBlock(trimmed, BlockType.REGULAR_TEXT, trimmed)
            }

            blocks.add(block)
        }

        return StructuredNote(title, blocks)
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