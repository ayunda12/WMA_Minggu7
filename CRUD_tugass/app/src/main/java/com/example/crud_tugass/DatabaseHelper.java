package com.example.crud_tugass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.crud_tugass.DatabaseSetting.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "data_teman.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TEMANLIST_TABLE = "CREATE TABLE " +
                DatabaseEntry.TABLE_NAME + " ( " +
                DatabaseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DatabaseEntry.COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                DatabaseEntry.COLUMN_ADDRESS +" TEXT NOT NULL, " +
                DatabaseEntry.COLUMN_TELEPHONE + " TEXT NOT NULL, " +
                DatabaseEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                DatabaseEntry.COLUMN_PHOTO + " TEXT NOT NULL, " +
                DatabaseEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TEMANLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
