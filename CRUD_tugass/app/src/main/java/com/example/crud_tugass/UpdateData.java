package com.example.crud_tugass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class UpdateData extends AppCompatActivity {
    private long temanID;
    EditText nama, tgl_lahir, tlp, alamat, umur, photo;
    Button btnUpdate;

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        databaseHelper = new DatabaseHelper(this);

        try {
            temanID = getIntent().getLongExtra("TEMAN_ID", 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        nama = findViewById(R.id.edt_upd_nama);
        tgl_lahir = findViewById(R.id.edt_upd_birthday);
        tlp = findViewById(R.id.edt_upd_telephone);
        alamat = findViewById(R.id.edt_upd_address);
        umur = findViewById(R.id.edt_upd_age);
        photo = findViewById(R.id.edt_upd_photo);
        btnUpdate = findViewById(R.id.btn_update_data);
        //untuk mengisi Edit Text
        getTeman(temanID);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    public void getTeman(long id){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseSetting.DatabaseEntry.TABLE_NAME + " WHERE _ID = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            nama.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_NAME)));
            tgl_lahir.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_BIRTHDAY)));
            tlp.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_TELEPHONE)));
            alamat.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_ADDRESS)));
            umur.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_AGE)));
            photo.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO)));
        }
    }

    public void updateData(){

        String name = nama.getText().toString().trim();
        String birthday = tgl_lahir.getText().toString().trim();
        String address = alamat.getText().toString().trim();
        String telephone = tlp.getText().toString().trim();
        String age = umur.getText().toString().trim();
        String img = photo.getText().toString().trim();

        if(name.isEmpty() || birthday.isEmpty() || address.isEmpty() ||
                telephone.isEmpty() || age.isEmpty() || img.isEmpty())
        {
            Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return;
        }

        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_NAME, name);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_BIRTHDAY, birthday);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_TELEPHONE, telephone);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_AGE, age);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO, img);

        int result = sqLiteDatabase.update(DatabaseSetting.DatabaseEntry.TABLE_NAME, contentValues,
                "_ID = ?", new String[] {String.valueOf(temanID)});

        if(result > 0)
            Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Data Gagal Diubah", Toast.LENGTH_LONG).show();

        Intent updateForm = new Intent(this, MainActivity.class);
        startActivity(updateForm);
    }
}
