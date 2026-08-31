package com.example.note2snap.data;

import java.lang.System;

@androidx.room.Dao
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00170\u0016H\'J\u0014\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00170\u0016H\'J\u0014\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00170\u001aH\'J\u001b\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00170\u00162\u0006\u0010\u001d\u001a\u00020\u001eH\'J\u0019\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010 \u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010!\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\"\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010#\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, d2 = {"Lcom/example/note2snap/data/AppDao;", "", "clearHistory", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFolder", "folder", "Lcom/example/note2snap/model/Folder;", "(Lcom/example/note2snap/model/Folder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNote", "note", "Lcom/example/note2snap/model/Note;", "(Lcom/example/note2snap/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNoteByTitle", "title", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteScanHistory", "history", "Lcom/example/note2snap/model/ScanHistory;", "(Lcom/example/note2snap/model/ScanHistory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFolders", "Lkotlinx/coroutines/flow/Flow;", "", "getAllNotes", "getAllScanHistory", "Landroidx/lifecycle/LiveData;", "getNoteByTitle", "getNotesByFolder", "folderId", "", "insertFolder", "insertNote", "insertScanHistory", "updateNote", "updateScanHistory", "app_debug"})
public abstract interface AppDao {
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM folders ORDER BY id DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note2snap.model.Folder>> getAllFolders();
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 1)
    public abstract java.lang.Object insertFolder(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.Folder folder, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Delete
    public abstract java.lang.Object deleteFolder(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.Folder folder, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM notes ORDER BY id DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note2snap.model.Note>> getAllNotes();
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM notes WHERE folderId = :folderId ORDER BY id DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note2snap.model.Note>> getNotesByFolder(int folderId);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 1)
    public abstract java.lang.Object insertNote(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Delete
    public abstract java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM notes WHERE title = :title")
    public abstract java.lang.Object deleteNoteByTitle(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 1)
    public abstract java.lang.Object insertScanHistory(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.ScanHistory history, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM scan_history ORDER BY timestamp DESC")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.note2snap.model.ScanHistory>> getAllScanHistory();
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM scan_history")
    public abstract java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM notes WHERE title = :title LIMIT 1")
    public abstract java.lang.Object getNoteByTitle(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.note2snap.model.Note> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Delete
    public abstract java.lang.Object deleteScanHistory(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.ScanHistory history, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Update
    public abstract java.lang.Object updateScanHistory(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.ScanHistory history, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Update
    public abstract java.lang.Object updateNote(@org.jetbrains.annotations.NotNull
    com.example.note2snap.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}