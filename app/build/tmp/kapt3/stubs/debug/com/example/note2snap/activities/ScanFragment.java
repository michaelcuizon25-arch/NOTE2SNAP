package com.example.note2snap.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J&\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002J \u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\rH\u0002J\u0012\u0010)\u001a\u0004\u0018\u00010\r2\u0006\u0010*\u001a\u00020&H\u0002J)\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020\r2\u0006\u0010\'\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J!\u0010/\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\r2\u0006\u0010\'\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0010\u00101\u001a\u00020\u00172\u0006\u00102\u001a\u00020\rH\u0002J\b\u00103\u001a\u00020\u0017H\u0002J\b\u00104\u001a\u00020\u0017H\u0002J\u000e\u00105\u001a\u00020\u00172\u0006\u00102\u001a\u00020\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\r0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\r0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00066"}, d2 = {"Lcom/example/note2snap/activities/ScanFragment;", "Landroidx/fragment/app/Fragment;", "()V", "camera", "Landroidx/camera/core/Camera;", "imageCapture", "Landroidx/camera/core/ImageCapture;", "isTorchOn", "", "loadingDialog", "Landroidx/appcompat/app/AlertDialog;", "requestCameraPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "scanStartTime", "", "selectImageFromGallery", "tvInstruction", "Landroid/widget/TextView;", "viewFinder", "Landroidx/camera/view/PreviewView;", "checkAndStartCamera", "", "hideLoadingDialog", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "preprocessBitmap", "Landroid/graphics/Bitmap;", "bitmap", "processImageWithOcr", "imageUri", "Landroid/net/Uri;", "imagePath", "fallbackTitle", "saveImageToInternalStorage", "uri", "saveNoteToDatabase", "title", "content", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveScanHistory", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "showLoadingDialog", "message", "startCamera", "takePhoto", "updateInstruction", "app_debug"})
public final class ScanFragment extends androidx.fragment.app.Fragment {
    private androidx.camera.view.PreviewView viewFinder;
    private android.widget.TextView tvInstruction;
    private androidx.camera.core.ImageCapture imageCapture;
    private androidx.camera.core.Camera camera;
    private boolean isTorchOn = false;
    private androidx.appcompat.app.AlertDialog loadingDialog;
    private long scanStartTime = 0L;
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> selectImageFromGallery = null;
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestCameraPermission = null;
    
    public ScanFragment() {
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
    
    public final void updateInstruction(@org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    private final void checkAndStartCamera() {
    }
    
    private final void startCamera() {
    }
    
    private final void takePhoto() {
    }
    
    private final android.graphics.Bitmap preprocessBitmap(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final void processImageWithOcr(android.net.Uri imageUri, java.lang.String imagePath, java.lang.String fallbackTitle) {
    }
    
    private final void showLoadingDialog(java.lang.String message) {
    }
    
    private final void hideLoadingDialog() {
    }
    
    private final java.lang.String saveImageToInternalStorage(android.net.Uri uri) {
        return null;
    }
    
    private final java.lang.Object saveNoteToDatabase(java.lang.String title, java.lang.String content, java.lang.String imagePath, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.Object saveScanHistory(java.lang.String title, java.lang.String imagePath, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}