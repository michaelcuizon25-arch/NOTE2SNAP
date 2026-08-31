package com.example.note2snap.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.note2snap.model.Folder;
import com.example.note2snap.model.Note;
import com.example.note2snap.model.ScanHistory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Folder> __insertionAdapterOfFolder;

  private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;

  private final EntityInsertionAdapter<ScanHistory> __insertionAdapterOfScanHistory;

  private final EntityDeletionOrUpdateAdapter<Folder> __deletionAdapterOfFolder;

  private final EntityDeletionOrUpdateAdapter<Note> __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<ScanHistory> __deletionAdapterOfScanHistory;

  private final EntityDeletionOrUpdateAdapter<ScanHistory> __updateAdapterOfScanHistory;

  private final EntityDeletionOrUpdateAdapter<Note> __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNoteByTitle;

  private final SharedSQLiteStatement __preparedStmtOfClearHistory;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFolder = new EntityInsertionAdapter<Folder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `folders` (`id`,`name`,`dateCreated`,`timestamp`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Folder entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDateCreated() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDateCreated());
        }
        statement.bindLong(4, entity.getTimestamp());
      }
    };
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`id`,`folderId`,`title`,`content`,`imagePath`,`dateEdited`,`isStarred`,`timestamp`,`fileSizeBytes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFolderId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getFolderId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContent());
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImagePath());
        }
        if (entity.getDateEdited() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDateEdited());
        }
        final int _tmp = entity.isStarred() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getTimestamp());
        statement.bindLong(9, entity.getFileSizeBytes());
      }
    };
    this.__insertionAdapterOfScanHistory = new EntityInsertionAdapter<ScanHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `scan_history` (`id`,`title`,`date`,`imagePath`,`isSyncedLocal`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScanHistory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getImagePath());
        }
        final int _tmp = entity.isSyncedLocal() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfFolder = new EntityDeletionOrUpdateAdapter<Folder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `folders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Folder entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfScanHistory = new EntityDeletionOrUpdateAdapter<ScanHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `scan_history` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScanHistory entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfScanHistory = new EntityDeletionOrUpdateAdapter<ScanHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `scan_history` SET `id` = ?,`title` = ?,`date` = ?,`imagePath` = ?,`isSyncedLocal` = ?,`timestamp` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScanHistory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getImagePath());
        }
        final int _tmp = entity.isSyncedLocal() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getTimestamp());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`folderId` = ?,`title` = ?,`content` = ?,`imagePath` = ?,`dateEdited` = ?,`isStarred` = ?,`timestamp` = ?,`fileSizeBytes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFolderId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getFolderId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContent());
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImagePath());
        }
        if (entity.getDateEdited() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDateEdited());
        }
        final int _tmp = entity.isStarred() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getTimestamp());
        statement.bindLong(9, entity.getFileSizeBytes());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteNoteByTitle = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE title = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearHistory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM scan_history";
        return _query;
      }
    };
  }

  @Override
  public Object insertFolder(final Folder folder, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFolder.insert(folder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNote.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertScanHistory(final ScanHistory history,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfScanHistory.insert(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteFolder(final Folder folder, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFolder.handle(folder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteScanHistory(final ScanHistory history,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfScanHistory.handle(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateScanHistory(final ScanHistory history,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfScanHistory.handle(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteNoteByTitle(final String title,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNoteByTitle.acquire();
        int _argIndex = 1;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, title);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteNoteByTitle.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object clearHistory(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearHistory.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearHistory.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Folder>> getAllFolders() {
    final String _sql = "SELECT * FROM folders ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"folders"}, new Callable<List<Folder>>() {
      @Override
      @NonNull
      public List<Folder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDateCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "dateCreated");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Folder> _result = new ArrayList<Folder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Folder _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDateCreated;
            if (_cursor.isNull(_cursorIndexOfDateCreated)) {
              _tmpDateCreated = null;
            } else {
              _tmpDateCreated = _cursor.getString(_cursorIndexOfDateCreated);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new Folder(_tmpId,_tmpName,_tmpDateCreated,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Note>> getAllNotes() {
    final String _sql = "SELECT * FROM notes ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfDateEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEdited");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Integer _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getInt(_cursorIndexOfFolderId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            final String _tmpDateEdited;
            if (_cursor.isNull(_cursorIndexOfDateEdited)) {
              _tmpDateEdited = null;
            } else {
              _tmpDateEdited = _cursor.getString(_cursorIndexOfDateEdited);
            }
            final boolean _tmpIsStarred;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            _item = new Note(_tmpId,_tmpFolderId,_tmpTitle,_tmpContent,_tmpImagePath,_tmpDateEdited,_tmpIsStarred,_tmpTimestamp,_tmpFileSizeBytes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Note>> getNotesByFolder(final int folderId) {
    final String _sql = "SELECT * FROM notes WHERE folderId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, folderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfDateEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEdited");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Integer _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getInt(_cursorIndexOfFolderId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            final String _tmpDateEdited;
            if (_cursor.isNull(_cursorIndexOfDateEdited)) {
              _tmpDateEdited = null;
            } else {
              _tmpDateEdited = _cursor.getString(_cursorIndexOfDateEdited);
            }
            final boolean _tmpIsStarred;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            _item = new Note(_tmpId,_tmpFolderId,_tmpTitle,_tmpContent,_tmpImagePath,_tmpDateEdited,_tmpIsStarred,_tmpTimestamp,_tmpFileSizeBytes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<ScanHistory>> getAllScanHistory() {
    final String _sql = "SELECT * FROM scan_history ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"scan_history"}, false, new Callable<List<ScanHistory>>() {
      @Override
      @Nullable
      public List<ScanHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfIsSyncedLocal = CursorUtil.getColumnIndexOrThrow(_cursor, "isSyncedLocal");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ScanHistory> _result = new ArrayList<ScanHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScanHistory _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            final boolean _tmpIsSyncedLocal;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSyncedLocal);
            _tmpIsSyncedLocal = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ScanHistory(_tmpId,_tmpTitle,_tmpDate,_tmpImagePath,_tmpIsSyncedLocal,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getNoteByTitle(final String title, final Continuation<? super Note> continuation) {
    final String _sql = "SELECT * FROM notes WHERE title = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Note>() {
      @Override
      @Nullable
      public Note call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfDateEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEdited");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final Note _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Integer _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getInt(_cursorIndexOfFolderId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            final String _tmpDateEdited;
            if (_cursor.isNull(_cursorIndexOfDateEdited)) {
              _tmpDateEdited = null;
            } else {
              _tmpDateEdited = _cursor.getString(_cursorIndexOfDateEdited);
            }
            final boolean _tmpIsStarred;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            _result = new Note(_tmpId,_tmpFolderId,_tmpTitle,_tmpContent,_tmpImagePath,_tmpDateEdited,_tmpIsStarred,_tmpTimestamp,_tmpFileSizeBytes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
