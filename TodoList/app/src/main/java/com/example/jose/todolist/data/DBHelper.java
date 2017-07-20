package com.example.jose.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jobs.db";
    private static final String TAG = "db helper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //4 columns id, description, due date, and category for table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_TODO.TABLE_NAME + " ("+
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE + " DATE, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY+ " TEXT NOT NULL DEFAULT 'Other' " + "); ";
        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    //drop table if its already in existence
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Contract.TABLE_TODO.TABLE_NAME);
    }


}
