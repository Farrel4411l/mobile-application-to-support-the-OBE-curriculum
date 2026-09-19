package com.example.projectfarrelmobileprogramming.ui.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOTE + " TEXT, " +
            COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void insertNote(String noteText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, noteText);
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_TIMESTAMP + " DESC", null);
        
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                );
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
}
