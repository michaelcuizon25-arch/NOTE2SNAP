package com.example.note2snap.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\"H\u0002J\u0010\u0010&\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0011H\u0002J\b\u0010(\u001a\u00020\"H\u0002J&\u0010)\u001a\u0004\u0018\u00010$2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u001a\u00100\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00101\u001a\u00020\"H\u0002J\b\u00102\u001a\u00020\"H\u0002J\u0010\u00103\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0011H\u0002J\u0010\u00104\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0011H\u0002J\u0010\u00105\u001a\u00020\"2\u0006\u00106\u001a\u00020\u001cH\u0002J\b\u00107\u001a\u00020\"H\u0002J\b\u00108\u001a\u00020\"H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/example/note2snap/activities/NotesFragment;", "Landroidx/fragment/app/Fragment;", "()V", "btnSort", "Landroid/widget/ImageView;", "chipAll", "Landroid/widget/TextView;", "chipFolders", "chipNotes", "currentFilter", "Lcom/example/note2snap/activities/FilterType;", "currentSort", "Lcom/example/note2snap/activities/SortType;", "folderAdapter", "Lcom/example/note2snap/adapter/FolderAdapter;", "folderList", "", "Lcom/example/note2snap/model/Folder;", "masterFolderList", "masterNotesList", "Lcom/example/note2snap/model/Note;", "notesAdapter", "Lcom/example/note2snap/adapter/NotesAdapter;", "notesList", "rvFolders", "Landroidx/recyclerview/widget/RecyclerView;", "rvNotes", "searchQuery", "", "tvFoldersLabel", "tvNotFound", "tvNotesLabel", "tvResultCount", "animateScreenSlideUp", "", "view", "Landroid/view/View;", "applySearchAndSort", "handleFolderClick", "folder", "observeDatabaseData", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "setupFilterListeners", "showBottomSheetMenu", "showDeleteFolderDialog", "showEditFolderDialog", "showNoFileFoundDialog", "folderName", "showSortBottomSheet", "updateFilterTabUI", "app_debug"})
public final class NotesFragment extends androidx.fragment.app.Fragment {
    private final java.util.List<com.example.note2snap.model.Note> masterNotesList = null;
    private final java.util.List<com.example.note2snap.model.Folder> masterFolderList = null;
    private final java.util.List<com.example.note2snap.model.Note> notesList = null;
    private final java.util.List<com.example.note2snap.model.Folder> folderList = null;
    private com.example.note2snap.adapter.NotesAdapter notesAdapter;
    private com.example.note2snap.adapter.FolderAdapter folderAdapter;
    private androidx.recyclerview.widget.RecyclerView rvNotes;
    private androidx.recyclerview.widget.RecyclerView rvFolders;
    private android.widget.TextView tvFoldersLabel;
    private android.widget.TextView tvNotesLabel;
    private android.widget.TextView tvResultCount;
    private android.widget.TextView tvNotFound;
    private android.widget.ImageView btnSort;
    private android.widget.TextView chipAll;
    private android.widget.TextView chipNotes;
    private android.widget.TextView chipFolders;
    private com.example.note2snap.activities.SortType currentSort = com.example.note2snap.activities.SortType.NAME;
    private com.example.note2snap.activities.FilterType currentFilter = com.example.note2snap.activities.FilterType.ALL;
    private java.lang.String searchQuery = "";
    
    public NotesFragment() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void animateScreenSlideUp(android.view.View view) {
    }
    
    private final void handleFolderClick(com.example.note2snap.model.Folder folder) {
    }
    
    private final void showNoFileFoundDialog(java.lang.String folderName) {
    }
    
    private final void setupFilterListeners() {
    }
    
    private final void updateFilterTabUI() {
    }
    
    private final void observeDatabaseData() {
    }
    
    private final void applySearchAndSort() {
    }
    
    private final void showSortBottomSheet() {
    }
    
    private final void showEditFolderDialog(com.example.note2snap.model.Folder folder) {
    }
    
    private final void showDeleteFolderDialog(com.example.note2snap.model.Folder folder) {
    }
    
    private final void showBottomSheetMenu() {
    }
}