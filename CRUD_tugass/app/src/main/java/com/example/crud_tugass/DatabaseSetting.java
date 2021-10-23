package com.example.crud_tugass;

import android.provider.BaseColumns;

public class DatabaseSetting {

    private DatabaseSetting() {}

    public static final class DatabaseEntry implements BaseColumns{
        public static final String TABLE_NAME = "temanList";
        public static final String COLUMN_NAME = "nama";
        public static final String COLUMN_BIRTHDAY = "tgl_lahir";
        public static final String COLUMN_ADDRESS = "alamat";
        public static final String COLUMN_TELEPHONE = "telepon";
        public static final String COLUMN_AGE = "umur";
        public static final String COLUMN_PHOTO = "foto";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}

