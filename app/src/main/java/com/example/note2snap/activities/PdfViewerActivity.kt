package com.example.note2snap.activities

import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.note2snap.R
import com.example.note2snap.data.AppDatabase
import com.example.note2snap.model.Note
import com.example.note2snap.utils.DocxExporter
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfViewerActivity : AppCompatActivity() {

    private var currentNote: Note? = null
    private var currentTitle: String = "Untitled Note"
    private var currentRawContent: String = ""
    private var currentImagePath: String = ""
    private var isEditMode: Boolean = false

    private val createPdfLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        uri?.let { writePdfToUri(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        currentTitle = intent.getStringExtra("TITLE") ?: "Untitled Note"
        val directContent = intent.getStringExtra("CONTENT")
        currentImagePath = intent.getStringExtra("IMAGE_PATH") ?: ""

        findViewById<TextView>(R.id.tvPdfTitle)?.text = currentTitle

        // 1. Display Scanned Image Preview
        setupImagePreview(currentImagePath)

        if (!directContent.isNullOrEmpty()) {
            currentRawContent = directContent
            renderContent(directContent)
            fetchNoteFromDatabase()
        } else {
            fetchNoteFromDatabase()
        }

        findViewById<ImageButton>(R.id.btnPdfBack)?.setOnClickListener { finish() }

        findViewById<ImageView>(R.id.btnPdfMoreOptions)?.setOnClickListener { view ->
            showOptionsMenu(view)
        }

        // 4. Setup Inline Formatting Toolbar Buttons
        findViewById<Button>(R.id.btnFormatBold)?.setOnClickListener { wrapSelectedText("<b>", "</b>") }
        findViewById<Button>(R.id.btnFormatItalic)?.setOnClickListener { wrapSelectedText("<i>", "</i>") }
        findViewById<Button>(R.id.btnFormatBullet)?.setOnClickListener { insertAtCursor("\n• ") }
        findViewById<Button>(R.id.btnFormatHeading)?.setOnClickListener { insertAtCursor("\n# ") }

        // 2 & 6. Primary Action: Direct Inline Save to Notes with Visual Feedback
        findViewById<MaterialButton>(R.id.btnActionSaveNotes)?.setOnClickListener {
            if (isEditMode) {
                val etInlineEditor = findViewById<EditText>(R.id.etInlineEditor)
                currentRawContent = etInlineEditor.text.toString().replace("\n", "<br/>")
                toggleInlineEditMode(false)
            }
            saveNoteToDatabase()
        }

        findViewById<LinearLayout>(R.id.btnActionDownload)?.setOnClickListener {
            val sanitizedFileName = currentTitle.replace("[^a-zA-Z0-9._-]".toRegex(), "_")
            createPdfLauncher.launch("$sanitizedFileName.pdf")
        }

        findViewById<LinearLayout>(R.id.btnActionShare)?.setOnClickListener {
            shareDocument()
        }
    }

    private fun setupImagePreview(path: String) {
        val cardImagePreview = findViewById<View>(R.id.cardImagePreview)
        val imgScannedThumbnail = findViewById<ImageView>(R.id.imgScannedThumbnail)

        if (path.isNotBlank()) {
            val imgFile = File(path)
            if (imgFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                imgScannedThumbnail?.setImageBitmap(bitmap)
                cardImagePreview?.visibility = View.VISIBLE
            } else {
                cardImagePreview?.visibility = View.GONE
            }
        } else {
            cardImagePreview?.visibility = View.GONE
        }
    }

    private fun isDarkMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun fetchNoteFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val note = AppDatabase.getDatabase(this@PdfViewerActivity).appDao().getNoteByTitle(currentTitle)
            note?.let {
                currentNote = it
                currentRawContent = it.content
                if (currentImagePath.isBlank() && it.imagePath.isNotBlank()) {
                    currentImagePath = it.imagePath
                }
                withContext(Dispatchers.Main) {
                    setupImagePreview(currentImagePath)
                    renderContent(it.content)
                }
            }
        }
    }

    private fun renderContent(rawContent: String) {
        val tvPdfContent = findViewById<TextView>(R.id.tvPdfContent)
        val webViewContent = findViewById<WebView>(R.id.webViewContent)
        val scrollViewContent = findViewById<View>(R.id.scrollViewContent)

        val dark = isDarkMode()
        val hasTable = rawContent.contains("<table", ignoreCase = true)

        if (hasTable && webViewContent != null) {
            scrollViewContent?.visibility = View.GONE
            tvPdfContent?.visibility = View.GONE
            webViewContent.visibility = View.VISIBLE

            val bgColor = if (dark) "#121212" else "#FFFFFF"
            val textColor = if (dark) "#E0E0E0" else "#000000"
            val headerBg = if (dark) "#1F1F1F" else "#F2F2F7"
            val borderColor = if (dark) "#333333" else "#CCCCCC"

            val styledHtml = """
                <html>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body { font-family: sans-serif; padding: 12px; color: $textColor; background-color: $bgColor; }
                        table { width: 100%; border-collapse: collapse; margin-top: 10px; margin-bottom: 10px; }
                        th { background-color: $headerBg; font-weight: bold; text-align: left; padding: 8px; border: 1px solid $borderColor; color: $textColor; }
                        td { padding: 8px; border: 1px solid $borderColor; vertical-align: top; color: $textColor; }
                    </style>
                </head>
                <body>
                    $rawContent
                </body>
                </html>
            """.trimIndent()

            webViewContent.setBackgroundColor(Color.parseColor(bgColor))
            webViewContent.webViewClient = WebViewClient()
            webViewContent.settings.javaScriptEnabled = false
            webViewContent.loadDataWithBaseURL(null, styledHtml, "text/html", "UTF-8", null)
        } else {
            webViewContent?.visibility = View.GONE
            scrollViewContent?.visibility = View.VISIBLE

            val htmlFormatted = rawContent
                .replace(Regex("\\*\\*(.*?)\\*\\*"), "<b>$1</b>")
                .replace("\n", "<br/>")

            tvPdfContent?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlFormatted, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(htmlFormatted)
            }

            tvPdfContent?.setTextColor(if (dark) Color.WHITE else Color.BLACK)
        }
    }

    // 5. Direct Inline Editing Switching
    private fun toggleInlineEditMode(enable: Boolean) {
        isEditMode = enable
        val tvPdfContent = findViewById<TextView>(R.id.tvPdfContent)
        val etInlineEditor = findViewById<EditText>(R.id.etInlineEditor)

        if (enable) {
            tvPdfContent?.visibility = View.GONE
            etInlineEditor?.visibility = View.VISIBLE
            etInlineEditor?.setText(cleanHtmlAndMarkdown(currentRawContent))
        } else {
            etInlineEditor?.visibility = View.GONE
            tvPdfContent?.visibility = View.VISIBLE
            renderContent(currentRawContent)
        }
    }

    private fun wrapSelectedText(startTag: String, endTag: String) {
        if (!isEditMode) toggleInlineEditMode(true)
        val etInlineEditor = findViewById<EditText>(R.id.etInlineEditor) ?: return
        val start = etInlineEditor.selectionStart.coerceAtLeast(0)
        val end = etInlineEditor.selectionEnd.coerceAtLeast(0)

        if (end > start) {
            val editable = etInlineEditor.text
            editable.insert(start, startTag)
            editable.insert(end + startTag.length, endTag)
        }
    }

    private fun insertAtCursor(textToInsert: String) {
        if (!isEditMode) toggleInlineEditMode(true)
        val etInlineEditor = findViewById<EditText>(R.id.etInlineEditor) ?: return
        val start = etInlineEditor.selectionStart.coerceAtLeast(0)
        etInlineEditor.text.insert(start, textToInsert)
    }

    private fun shareDocument() {
        DocxExporter.shareAsDocx(
            context = this,
            title = currentTitle,
            content = cleanHtmlAndMarkdown(currentRawContent)
        )
        showConfirmationFeedback("Share menu opened!")
    }

    private fun cleanHtmlAndMarkdown(text: String): String {
        return text.replace(Regex("<br\\s*/?>"), "\n")
            .replace(Regex("</p>"), "\n")
            .replace(Regex("</tr>"), "\n")
            .replace(Regex("</td>"), " | ")
            .replace(Regex("<[^>]*>"), "")
            .replace("**", "")
            .replace(Regex("&nbsp;"), " ")
            .trim()
    }

    private fun showOptionsMenu(anchorView: View) {
        val popup = PopupMenu(this, anchorView)

        popup.menu.add(0, 1, 0, if (isEditMode) "Done Editing" else "Edit Inline")
        popup.menu.add(0, 2, 1, "Rename Note")
        popup.menu.add(0, 3, 2, "Delete Note")

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> {
                    toggleInlineEditMode(!isEditMode)
                    true
                }
                2 -> {
                    showRenameDialog()
                    true
                }
                3 -> {
                    showDeleteConfirmationDialog()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showRenameDialog() {
        val input = EditText(this).apply {
            setText(currentTitle)
            setSelection(currentTitle.length)
            setPadding(40, 32, 40, 32)
        }

        AlertDialog.Builder(this)
            .setTitle("Rename Note")
            .setView(input)
            .setPositiveButton("Save") { d, _ ->
                val newTitle = input.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    currentTitle = newTitle
                    findViewById<TextView>(R.id.tvPdfTitle)?.text = newTitle
                    saveNoteToDatabase()
                }
                d.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { d, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    currentNote?.let {
                        AppDatabase.getDatabase(this@PdfViewerActivity).appDao().deleteNote(it)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PdfViewerActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                d.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveNoteToDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@PdfViewerActivity).appDao()
            val existingNote = currentNote

            val dateStr = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())

            if (existingNote != null) {
                val updatedNote = existingNote.copy(
                    title = currentTitle,
                    content = currentRawContent,
                    imagePath = currentImagePath,
                    dateEdited = dateStr
                )
                db.updateNote(updatedNote)
                currentNote = updatedNote
            } else {
                val newNote = Note(
                    title = currentTitle,
                    content = currentRawContent,
                    imagePath = currentImagePath,
                    dateEdited = dateStr
                )
                db.insertNote(newNote)
                currentNote = newNote
            }

            withContext(Dispatchers.Main) {
                // 6. Visual Confirmation State Feedback
                showConfirmationFeedback("✓ Saved to Notes successfully!")
            }
        }
    }

    private fun writePdfToUri(uri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val pdfDocument = PdfDocument()
                val pageWidth = 595
                val pageHeight = 842
                val margin = 40f
                val printableWidth = (pageWidth - (margin * 2)).toInt()

                var pageNumber = 1
                var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                var page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas

                val titlePaint = TextPaint().apply {
                    textSize = 18f
                    color = Color.BLACK
                    isFakeBoldText = true
                    isAntiAlias = true
                }

                val bodyPaint = TextPaint().apply {
                    textSize = 12f
                    color = Color.BLACK
                    isAntiAlias = true
                }

                var currentY = margin

                val titleLayout = createStaticLayout(currentTitle, titlePaint, printableWidth)
                canvas.save()
                canvas.translate(margin, currentY)
                titleLayout.draw(canvas)
                canvas.restore()

                currentY += titleLayout.height + 20f

                val cleanContent = cleanHtmlAndMarkdown(currentRawContent)
                val lines = cleanContent.lines()

                for (line in lines) {
                    val lineLayout = createStaticLayout(line, bodyPaint, printableWidth)

                    if (currentY + lineLayout.height > pageHeight - margin) {
                        pdfDocument.finishPage(page)
                        pageNumber++
                        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                        page = pdfDocument.startPage(pageInfo)
                        canvas = page.canvas
                        currentY = margin
                    }

                    canvas.save()
                    canvas.translate(margin, currentY)
                    lineLayout.draw(canvas)
                    canvas.restore()

                    currentY += lineLayout.height + 4f
                }

                pdfDocument.finishPage(page)

                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }
                pdfDocument.close()

                withContext(Dispatchers.Main) {
                    showConfirmationFeedback("✓ PDF saved successfully!")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PdfViewerActivity, "Failed to save PDF.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createStaticLayout(text: String, paint: TextPaint, width: Int): StaticLayout {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, paint, width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
        }
    }

    // 6. Floating Green Confirmation Snackbar
    private fun showConfirmationFeedback(message: String) {
        val bottomBar = findViewById<View>(R.id.bottomBar) ?: return
        Snackbar.make(bottomBar, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(0xFF166534.toInt())
            .setTextColor(0xFFFFFFFF.toInt())
            .show()
    }
}