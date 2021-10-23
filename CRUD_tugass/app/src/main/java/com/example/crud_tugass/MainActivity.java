package com.example.crud_tugass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    TemanAdapter temanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        temanAdapter = new TemanAdapter(this, getAllItems());

        RecyclerView recyclerView = findViewById(R.id.recycler_teman);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(temanAdapter);

        temanAdapter.setOnItemClickListener(new TemanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {
                Intent detailData = new Intent(MainActivity.this, DetailData.class);
                detailData.putExtra("TEMAN_ID", id);
                startActivity(detailData);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteData((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showCreateForm = new Intent(MainActivity.this, CreateData.class);
                startActivity(showCreateForm);
            }
        });
    }

    private Cursor getAllItems(){
        return sqLiteDatabase.query(DatabaseSetting.DatabaseEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseSetting.DatabaseEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    private void deleteData(final long id){
        new AlertDialog.Builder(this)
                .setTitle("Konfrimasi")
                .setMessage("Yakin hapus data ini ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = sqLiteDatabase.delete(DatabaseSetting.DatabaseEntry.TABLE_NAME,
                                "_ID = " + id, null);

                        if(result > 0){
                            Toast.makeText(MainActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Data Gagal Dihapus", Toast.LENGTH_LONG).show();
                        }
                        dialogInterface.cancel();
                        temanAdapter.swapCursor(getAllItems());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        temanAdapter.swapCursor(getAllItems());
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmDelete(){

    }
}
