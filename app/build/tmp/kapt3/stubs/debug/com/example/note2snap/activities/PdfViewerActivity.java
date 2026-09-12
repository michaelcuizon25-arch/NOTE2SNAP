package com.example.note2snap.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0005H\u0002J\b\u0010\u001a\u001a\u00020\rH\u0002J\u0012\u0010\u001b\u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0005H\u0002J\b\u0010 \u001a\u00020\u0017H\u0002J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0005H\u0002J\b\u0010#\u001a\u00020\u0017H\u0002J\u0010\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u0005H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\u0010\u0010\'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)H\u0002J\b\u0010*\u001a\u00020\u0017H\u0002J\u0010\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\rH\u0002J\u0018\u0010-\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u0005H\u0002J\u0010\u00100\u001a\u00020\u00172\u0006\u00101\u001a\u000202H\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/example/note2snap/activities/PdfViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "createPdfLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "currentImagePath", "currentNote", "Lcom/example/note2snap/model/Note;", "currentRawContent", "currentTitle", "isEditMode", "", "cleanHtmlAndMarkdown", "text", "createStaticLayout", "Landroid/text/StaticLayout;", "paint", "Landroid/text/TextPaint;", "width", "", "fetchNoteFromDatabase", "", "insertAtCursor", "textToInsert", "isDarkMode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "renderContent", "rawContent", "saveNoteToDatabase", "setupImagePreview", "path", "shareDocument", "showConfirmationFeedback", "message", "showDeleteConfirmationDialog", "showOptionsMenu", "anchorView", "Landroid/view/View;", "showRenameDialog", "toggleInlineEditMode", "enable", "wrapSelectedText", "startTag", "endTag", "writePdfToUri", "uri", "Landroid/net/Uri;", "app_debug"})
public final class PdfViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.note2snap.model.Note currentNote;
    private java.lang.String currentTitle = "Untitled Note";
    private java.lang.String currentRawContent = "";
    private java.lang.String currentImagePath = "";
    private boolean isEditMode = false;
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> createPdfLauncher = null;
    
    public PdfViewerActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupImagePreview(java.lang.String path) {
    }
    
    private final boolean isDarkMode() {
        return false;
    }
    
    private final void fetchNoteFromDatabase() {
    }
    
    private final void renderContent(java.lang.String rawContent) {
    }
    
    private final void toggleInlineEditMode(boolean enable) {
    }
    
    private final void wrapSelectedText(java.lang.String startTag, java.lang.String endTag) {
    }
    
    private final void insertAtCursor(java.lang.String textToInsert) {
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
    
    private final void showDeleteConfirmationDialog() {
    }
    
    private final void saveNoteToDatabase() {
    }
    
    private final void writePdfToUri(android.net.Uri uri) {
    }
    
    private final android.text.StaticLayout createStaticLayout(java.lang.String text, android.text.TextPaint paint, int width) {
        return null;
    }
    
    private final void showConfirmationFeedback(java.lang.String message) {
    }
}