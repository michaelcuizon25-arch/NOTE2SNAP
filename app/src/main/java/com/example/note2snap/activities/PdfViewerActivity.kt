package com.example.note2snap.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.note2snap.R
import com.example.note2snap.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PdfViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        val title = intent.getStringExtra("TITLE") ?: "Untitled Note"
        val directContent = intent.getStringExtra("CONTENT")

        findViewById<TextView>(R.id.tvPdfTitle).text = title

        // 1. If content was passed directly via intent (from ScanFragment), render it immediately
        if (!directContent.isNullOrEmpty()) {
            renderContent(directContent)
        } else {
            // 2. Otherwise (from Home/History), fetch content from Room Database by title
            lifecycleScope.launch(Dispatchers.IO) {
                val note = AppDatabase.getDatabase(this@PdfViewerActivity).appDao().getNoteByTitle(title)
                val content = note?.content ?: "No content available."

                withContext(Dispatchers.Main) {
                    renderContent(content)
                }
            }
        }

        // Setup button click listeners
        findViewById<ImageButton>(R.id.btnPdfBack)?.setOnClickListener { finish() }
        findViewById<LinearLayout>(R.id.btnActionSaveNotes)?.setOnClickListener {
            Toast.makeText(this, "Saved to Notes", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.btnActionDownload)?.setOnClickListener {
            Toast.makeText(this, "Downloading PDF...", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.btnActionShare)?.setOnClickListener {
            Toast.makeText(this, "Sharing document...", Toast.LENGTH_SHORT).show()
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

        // Ensures text color adapts strictly to light/dark themes
        tvPdfContent.setTextColor(ContextCompat.getColor(this, R.color.text_main))
    }
}