package com.example.note2snap.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.note2snap.R
import com.example.note2snap.data.AppDatabase
import com.example.note2snap.model.Note
import com.example.note2snap.utils.DocxExporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PdfViewerActivity : AppCompatActivity() {

    private var currentNote: Note? = null
    private var currentTitle: String = "Untitled Note"
    private var currentRawContent: String = ""

    // Storage Access Framework launcher to let users select destination folder (Downloads/Documents)
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

        findViewById<TextView>(R.id.tvPdfTitle).apply {
            text = currentTitle
            // Tap title to rename or delete
            setOnClickListener { showRenameOrDeleteDialog() }
        }

        // Load Note Content
        if (!directContent.isNullOrEmpty()) {
            currentRawContent = directContent
            renderContent(directContent)
            fetchNoteFromDatabase()
        } else {
            fetchNoteFromDatabase()
        }

        // Setup button click listeners
        findViewById<ImageButton>(R.id.btnPdfBack)?.setOnClickListener { finish() }

        // Action: Edit Note Content
        findViewById<LinearLayout>(R.id.btnActionSaveNotes)?.setOnClickListener {
            showEditContentDialog()
        }

        // Action: Download PDF to user-selected folder
        findViewById<LinearLayout>(R.id.btnActionDownload)?.setOnClickListener {
            val sanitizedFileName = currentTitle.replace("[^a-zA-Z0-9._-]".toRegex(), "_")
            createPdfLauncher.launch("$sanitizedFileName.pdf")
        }

        // Action: Native Share as DOCX File
        findViewById<LinearLayout>(R.id.btnActionShare)?.setOnClickListener {
            shareDocument()
        }
    }

    private fun fetchNoteFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val note = AppDatabase.getDatabase(this@PdfViewerActivity).appDao().getNoteByTitle(currentTitle)
            note?.let {
                currentNote = it
                currentRawContent = it.content
                withContext(Dispatchers.Main) {
                    renderContent(it.content)
                }
            }
        }
    }

    private fun renderContent(rawContent: String) {
        val tvPdfContent = findViewById<TextView>(R.id.tvPdfContent) ?: return
        val htmlFormatted = rawContent
            .replace(Regex("\\*\\*(.*?)\\*\\*"), "<b>$1</b>")
            .replace("\n", "<br/>")

        tvPdfContent.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlFormatted, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(htmlFormatted)
        }

        tvPdfContent.setTextColor(ContextCompat.getColor(this, R.color.text_main))
    }

    private fun shareDocument() {
        DocxExporter.shareAsDocx(
            context = this,
            title = currentTitle,
            content = currentRawContent
        )
    }

    private fun writePdfToUri(uri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val pdfDocument = PdfDocument()
                val pageWidth = 595   // Standard A4 width (points)
                val pageHeight = 842  // Standard A4 height (points)
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

                // Draw Title with word wrapping
                val titleLayout = createStaticLayout(currentTitle, titlePaint, printableWidth)
                canvas.save()
                canvas.translate(margin, currentY)
                titleLayout.draw(canvas)
                canvas.restore()

                currentY += titleLayout.height + 20f

                // Draw Content line by line with multi-page support
                val cleanContent = currentRawContent.replace("**", "")
                val lines = cleanContent.lines()

                for (line in lines) {
                    val lineLayout = createStaticLayout(line, bodyPaint, printableWidth)

                    // Check page height limit to create a new page
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
                    Toast.makeText(this@PdfViewerActivity, "PDF saved successfully!", Toast.LENGTH_SHORT).show()
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

    private fun showRenameOrDeleteDialog() {
        val options = arrayOf("Rename Note", "Delete Note")
        AlertDialog.Builder(this)
            .setTitle(currentTitle)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showRenameDialog()
                    1 -> showDeleteConfirmationDialog()
                }
            }
            .show()
    }

    private fun showRenameDialog() {
        val input = EditText(this).apply { setText(currentTitle) }
        AlertDialog.Builder(this)
            .setTitle("Rename Note")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = input.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    currentTitle = newTitle
                    findViewById<TextView>(R.id.tvPdfTitle).text = newTitle
                    saveNoteToDatabase()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditContentDialog() {
        val input = EditText(this).apply {
            setText(currentRawContent)
            setSelection(currentRawContent.length)
        }
        AlertDialog.Builder(this)
            .setTitle("Edit Note Content")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                currentRawContent = input.text.toString()
                renderContent(currentRawContent)
                saveNoteToDatabase()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    currentNote?.let {
                        AppDatabase.getDatabase(this@PdfViewerActivity).appDao().deleteNote(it)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PdfViewerActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveNoteToDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@PdfViewerActivity).appDao()
            val existingNote = currentNote

            if (existingNote != null) {
                val updatedNote = existingNote.copy(title = currentTitle, content = currentRawContent)
                db.updateNote(updatedNote)
                currentNote = updatedNote
            } else {
                val newNote = Note(
                    title = currentTitle,
                    content = currentRawContent,
                    imagePath = intent.getStringExtra("IMAGE_PATH") ?: "",
                    dateEdited = "Updated"
                )
                db.insertNote(newNote)
                currentNote = newNote
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@PdfViewerActivity, "Note updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}