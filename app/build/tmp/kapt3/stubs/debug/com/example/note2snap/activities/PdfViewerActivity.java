package com.example.note2snap.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0005H\u0002J\b\u0010\u001c\u001a\u00020\u0014H\u0002J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\b\u0010\u001e\u001a\u00020\u0014H\u0002J\b\u0010\u001f\u001a\u00020\u0014H\u0002J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u0014H\u0002J\u0010\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&H\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/example/note2snap/activities/PdfViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "createPdfLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "currentNote", "Lcom/example/note2snap/model/Note;", "currentRawContent", "currentTitle", "cleanHtmlAndMarkdown", "text", "createStaticLayout", "Landroid/text/StaticLayout;", "paint", "Landroid/text/TextPaint;", "width", "", "fetchNoteFromDatabase", "", "isDarkMode", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "renderContent", "rawContent", "saveNoteToDatabase", "shareDocument", "showDeleteConfirmationDialog", "showEditContentDialog", "showOptionsMenu", "anchorView", "Landroid/view/View;", "showRenameDialog", "writePdfToUri", "uri", "Landroid/net/Uri;", "app_debug"})
public final class PdfViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.note2snap.model.Note currentNote;
    private java.lang.String currentTitle = "Untitled Note";
    private java.lang.String currentRawContent = "";
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> createPdfLauncher = null;
    
    public PdfViewerActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final boolean isDarkMode() {
        return false;
    }
    
    private final void fetchNoteFromDatabase() {
    }
    
    private final void renderContent(java.lang.String rawContent) {
    }
    
    private final void shareDocument() {
    }
    
    private final java.lang.String cleanHtmlAndMarkdown(java.lang.String text) {
        return null;
    }
    
    private final void showOptionsMenu(android.view.View anchorView) {
    }
    
    private final void showRenameDialog() {
    }
    
    private final void showEditContentDialog() {
    }
    
    private final void showDeleteConfirmationDialog() {
    }
    
    private final void saveNoteToDatabase() {
    }
    
    private final void writePdfToUri(android.net.Uri uri) {
    }
    
    private final android.text.StaticLayout createStaticLayout(java.lang.String text, android.text.TextPaint paint, int width) {
        return null;
    }
}