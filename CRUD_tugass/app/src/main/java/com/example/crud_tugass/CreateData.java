package com.example.crud_tugass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateData extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    EditText editName, editBirthday, editAddress, editTelephone, editAge, editPhoto;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        editName = findViewById(R.id.edt_nama);
        editBirthday = findViewById(R.id.edt_birthday);
        editAddress = findViewById(R.id.edt_address);
        editTelephone = findViewById(R.id.edt_telephone);
        editAge = findViewById(R.id.edt_age);
        editPhoto = findViewById(R.id.edt_photo);

        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createData();
            }
        });
    }

    private void createData(){
        // MISALNYA kita atur nama, tanggal lahir, tlp, dan foto tidak boleh kosong
        String name = editName.getText().toString().trim();
        String birthday = editBirthday.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String telephone = editTelephone.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String photo = editPhoto.getText().toString().trim();

        if(name.isEmpty() || birthday.isEmpty() || address.isEmpty() ||
                telephone.isEmpty() || age.isEmpty() || photo.isEmpty())
        {
            Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_NAME, name);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_BIRTHDAY, birthday);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_TELEPHONE, telephone);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_AGE, age);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO, photo);

        long result = sqLiteDatabase.insert(DatabaseSetting.DatabaseEntry.TABLE_NAME,
                null, contentValues);

        if(result == -1)
            Toast.makeText(CreateData.this, "Data Gagal Disimpan", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(CreateData.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

        Intent createForm = new Intent(CreateData.this, MainActivity.class);
        startActivity(createForm);
    }
}
