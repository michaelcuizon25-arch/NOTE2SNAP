package com.example.note2snap.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note2snap.R
import com.example.note2snap.adapter.FolderAdapter
import com.example.note2snap.adapter.NotesAdapter
import com.example.note2snap.data.AppDatabase
import com.example.note2snap.model.Folder
import com.example.note2snap.model.Note
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class SortType { NAME, TIME, SIZE, TYPE }

class NotesFragment : Fragment() {

    private val masterNotesList = mutableListOf<Note>()
    private val masterFolderList = mutableListOf<Folder>()
    private val notesList = mutableListOf<Note>()
    private val folderList = mutableListOf<Folder>()

    private lateinit var notesAdapter: NotesAdapter
    private lateinit var folderAdapter: FolderAdapter

    private lateinit var rvNotes: RecyclerView
    private var rvFolders: RecyclerView? = null
    private var tvResultCount: TextView? = null
    private var tvNotFound: TextView? = null
    private var btnSort: ImageView? = null

    private var currentSort = SortType.NAME
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        rvNotes = view.findViewById(R.id.rvNotes)
        rvFolders = view.findViewById(R.id.rvFolders)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddNotes)
        val etSearch = view.findViewById<EditText>(R.id.etSearchNotes)

        tvResultCount = view.findViewById(R.id.tvResultCount)
        tvNotFound = view.findViewById(R.id.tvNotFound)
        btnSort = view.findViewById(R.id.btnSort)

        notesAdapter = NotesAdapter(notesList) { note ->
            val intent = Intent(context, PdfViewerActivity::class.java)
            intent.putExtra("TITLE", note.title)
            intent.putExtra("IMAGE_PATH", note.imagePath)
            startActivity(intent)
        }
        rvNotes.layoutManager = LinearLayoutManager(context)
        rvNotes.adapter = notesAdapter

        folderAdapter = FolderAdapter(
            folderList = folderList,
            onItemClick = { _ -> },
            onEditClick = { folder -> showEditFolderDialog(folder) },
            onDeleteClick = { folder -> showDeleteFolderDialog(folder) }
        )
        rvFolders?.layoutManager = LinearLayoutManager(context)
        rvFolders?.adapter = folderAdapter

        fabAdd?.setOnClickListener { showBottomSheetMenu() }

        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s.toString().trim()
                applySearchAndSort()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnSort?.setOnClickListener { showSortBottomSheet() }

        observeDatabaseData()

        return view
    }

    private fun observeDatabaseData() {
        val dao = AppDatabase.getDatabase(requireContext()).appDao()

        lifecycleScope.launch {
            dao.getAllFolders().collectLatest { fetchedFolders ->
                masterFolderList.clear()
                masterFolderList.addAll(fetchedFolders)
                applySearchAndSort()
            }
        }

        lifecycleScope.launch {
            dao.getAllNotes().collectLatest { fetchedNotes ->
                masterNotesList.clear()
                masterNotesList.addAll(fetchedNotes)
                applySearchAndSort()
            }
        }
    }

    private fun applySearchAndSort() {
        // 1. Filter and Sort Folders
        var filteredFolders = if (searchQuery.isEmpty()) {
            masterFolderList
        } else {
            masterFolderList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        filteredFolders = when (currentSort) {
            SortType.NAME -> filteredFolders.sortedBy { it.name.lowercase() }
            SortType.TIME -> filteredFolders.sortedByDescending { it.id } // Newest created folders first
            SortType.SIZE -> filteredFolders.sortedBy { it.name.lowercase() } // Folders don't have file sizes
            SortType.TYPE -> filteredFolders.sortedBy { it.name.lowercase() } // Folders don't have extensions
        }

        folderList.clear()
        folderList.addAll(filteredFolders)
        folderAdapter.notifyDataSetChanged()

        // 2. Filter and Sort Notes
        var filteredNotes = if (searchQuery.isEmpty()) {
            masterNotesList
        } else {
            masterNotesList.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }

        filteredNotes = when (currentSort) {
            SortType.NAME -> filteredNotes.sortedBy { it.title.lowercase() }
            SortType.TIME -> filteredNotes.sortedByDescending { it.timestamp }
            SortType.SIZE -> filteredNotes.sortedByDescending { it.fileSizeBytes }
            SortType.TYPE -> filteredNotes.sortedBy { it.fileType.lowercase() }
        }

        notesList.clear()
        notesList.addAll(filteredNotes)
        notesAdapter.notifyDataSetChanged()

        // 3. Update UI & Counts
        val totalItems = folderList.size + notesList.size

        if (totalItems == 0) {
            rvNotes.visibility = View.GONE
            rvFolders?.visibility = View.GONE
            tvResultCount?.visibility = View.GONE
            tvNotFound?.visibility = View.VISIBLE
        } else {
            rvNotes.visibility = if (notesList.isNotEmpty()) View.VISIBLE else View.GONE
            rvFolders?.visibility = if (folderList.isNotEmpty()) View.VISIBLE else View.GONE
            tvNotFound?.visibility = View.GONE

            if (searchQuery.isNotEmpty()) {
                tvResultCount?.visibility = View.VISIBLE
                tvResultCount?.text = "FOUND $totalItems ITEMS"
            } else {
                tvResultCount?.visibility = View.GONE
            }
        }
    }

    private fun showSortBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_sort_by, null, false)

        dialogView.findViewById<TextView>(R.id.tvSortName)?.setOnClickListener {
            currentSort = SortType.NAME
            applySearchAndSort()
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.tvSortTime)?.setOnClickListener {
            currentSort = SortType.TIME
            applySearchAndSort()
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.tvSortSize)?.setOnClickListener {
            currentSort = SortType.SIZE
            applySearchAndSort()
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.tvSortType)?.setOnClickListener {
            currentSort = SortType.TYPE
            applySearchAndSort()
            dialog.dismiss()
        }

        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun showEditFolderDialog(folder: Folder) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Folder Name")

        val input = EditText(requireContext())
        input.setText(folder.name)
        input.setSelection(folder.name.length)
        builder.setView(input)

        builder.setPositiveButton("Save") { dialog, _ ->
            val newName = input.text.toString().trim()
            if (newName.isNotEmpty()) {
                val updatedFolder = folder.copy(name = newName)
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabase(requireContext()).appDao().insertFolder(updatedFolder)
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Folder updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun showDeleteFolderDialog(folder: Folder) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Folder")
            .setMessage("Are you sure you want to delete '${folder.name}'?")
            .setPositiveButton("Delete") { dialog, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabase(requireContext()).appDao().deleteFolder(folder)
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Folder deleted", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showBottomSheetMenu() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_options, null, false)

        dialogView.findViewById<LinearLayout>(R.id.llOptionFolder)?.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(context, CreateFolderActivity::class.java))
        }

        dialogView.findViewById<LinearLayout>(R.id.llOptionNote)?.setOnClickListener {
            dialog.dismiss()
            (activity as? MainActivity)?.loadFragment(ScanFragment())
        }

        dialog.setContentView(dialogView)
        dialog.show()
    }
}