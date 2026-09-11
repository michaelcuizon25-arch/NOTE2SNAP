package com.example.note2snap.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note2snap.R
import com.example.note2snap.adapter.HistoryAdapter
import com.example.note2snap.data.AppDatabase
import com.example.note2snap.model.ScanHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var rvHistory: RecyclerView? = null
    private var llEmptyHistory: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Slide up the entire fragment screen on entry
        animateScreenSlideUp(view)

        rvHistory = view.findViewById(R.id.rvHistory)
        llEmptyHistory = view.findViewById(R.id.llEmptyHistory)

        rvHistory?.layoutManager = LinearLayoutManager(requireContext())

        // Observe Room database for real-time history updates
        AppDatabase.getDatabase(requireContext()).appDao().getAllScanHistory()
            .observe(viewLifecycleOwner, Observer { historyList ->
                if (historyList.isNullOrEmpty()) {
                    llEmptyHistory?.visibility = View.VISIBLE
                    rvHistory?.visibility = View.GONE
                } else {
                    llEmptyHistory?.visibility = View.GONE
                    rvHistory?.visibility = View.VISIBLE

                    rvHistory?.adapter = HistoryAdapter(
                        historyList = historyList,
                        onItemClick = { item ->
                            val intent = Intent(requireContext(), PdfViewerActivity::class.java).apply {
                                putExtra("TITLE", item.title)
                                putExtra("IMAGE_PATH", item.imagePath)
                            }
                            startActivity(intent)
                        },
                        onItemLongClick = { item ->
                            showOptionsDialog(item)
                        }
                    )

                    // Trigger cascade animation on history list items
                    rvHistory?.scheduleLayoutAnimation()
                }
            })
    }

    private fun animateScreenSlideUp(view: View) {
        view.translationY = 60f
        view.alpha = 0f
        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(350)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    private fun showOptionsDialog(item: ScanHistory) {
        val options = arrayOf("Edit Title", "Delete Note")

        AlertDialog.Builder(requireContext())
            .setTitle(item.title)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditTitleDialog(item)
                    1 -> confirmDeleteNote(item)
                }
            }
            .show()
    }

    private fun showEditTitleDialog(item: ScanHistory) {
        val input = EditText(requireContext()).apply {
            setText(item.title)
            setSelection(item.title.length)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Rename Scan")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = input.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val updated = item.copy(title = newTitle)
                        AppDatabase.getDatabase(requireContext()).appDao().updateScanHistory(updated)
                    }
                } else {
                    Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteNote(item: ScanHistory) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Scan?")
            .setMessage("Are you sure you want to delete \"${item.title}\"?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabase(requireContext()).appDao().deleteScanHistory(item)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}