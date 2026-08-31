package com.example.note2snap.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0014B;\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/note2snap/adapter/HistoryAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/note2snap/adapter/HistoryAdapter$HistoryViewHolder;", "historyList", "", "Lcom/example/note2snap/model/ScanHistory;", "onItemClick", "Lkotlin/Function1;", "", "onItemLongClick", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "HistoryViewHolder", "app_debug"})
public final class HistoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.note2snap.adapter.HistoryAdapter.HistoryViewHolder> {
    private final java.util.List<com.example.note2snap.model.ScanHistory> historyList = null;
    private final kotlin.jvm.functions.Function1<com.example.note2snap.model.ScanHistory, kotlin.Unit> onItemClick = null;
    private final kotlin.jvm.functions.Function1<com.example.note2snap.model.ScanHistory, kotlin.Unit> onItemLongClick = null;
    
    public HistoryAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.note2snap.model.ScanHistory> historyList, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.note2snap.model.ScanHistory, kotlin.Unit> onItemClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.note2snap.model.ScanHistory, kotlin.Unit> onItemLongClick) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.example.note2snap.adapter.HistoryAdapter.HistoryViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.note2snap.adapter.HistoryAdapter.HistoryViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/example/note2snap/adapter/HistoryAdapter$HistoryViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "tvScanDate", "Landroid/widget/TextView;", "getTvScanDate", "()Landroid/widget/TextView;", "tvScanSyncStatus", "getTvScanSyncStatus", "tvScanTitle", "getTvScanTitle", "app_debug"})
    public static final class HistoryViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvScanTitle = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvScanSyncStatus = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvScanDate = null;
        
        public HistoryViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvScanTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvScanSyncStatus() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvScanDate() {
            return null;
        }
    }
}