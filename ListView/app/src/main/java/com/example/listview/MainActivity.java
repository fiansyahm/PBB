package com.example.listview;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    kontakAdapter Kontakadapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;
    ArrayList<kontak> listKontak = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.listView);
//        ArrayList<kontak> listKontak = new ArrayList<>();
        Kontakadapter= new kontakAdapter(this,R.layout.item_user,listKontak);
        lv.setAdapter(Kontakadapter);

//        kontak newKontak1 = new kontak("budi","081111");
//        kontak newKontak2 = new kontak("agus","082222");
//        Kontakadapter.add(newKontak1);
//        Kontakadapter.add(newKontak2);
//
        dbopen = new SQLiteOpenHelper(this,"kontak.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db)
            {
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
        };
        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("create table if not exists kontak(nama TEXT, nohp TEXT);");
        ambildata();
    }
    private void add_item(String nm, String hp)
    {
        Cursor cur = dbku.rawQuery("select * from kontak where nama='" + nm.trim() + "'", null);
        if (cur.getCount() <= 0) {
            ContentValues datanya = new ContentValues();
            datanya.put("nama",nm);
            datanya.put("nohp",hp);
            dbku.insert("kontak",null,datanya);
            kontak newKontak = new kontak(nm,hp);
            Kontakadapter.add(newKontak);
            Toast.makeText(getApplicationContext(), "Kontak Ditambakan", Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void delete_item(String nm, String hp)
    {
        Cursor cur = dbku.rawQuery("select * from kontak where nama='" + nm.trim() + "'", null);
        if (cur.getCount() > 0) {
            ContentValues datanya = new ContentValues();
            datanya.put("nama",nm);
            datanya.put("nohp",hp);
            dbku.delete("kontak","nama='"+nm+"'",null);
            listKontak.removeIf(kontak -> kontak.banding(nm)==1);

//            refresh an
            kontak newKontak = new kontak("obladi","oblada");
            Kontakadapter.add(newKontak);
            Kontakadapter.remove(newKontak);
            Toast.makeText(getApplicationContext(), "Kontak Dihapus", Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void edit_item(String nm, String hp)
    {
        Cursor cur = dbku.rawQuery("select * from kontak where nama='" + nm.trim() + "'", null);
        if (cur.getCount() > 0) {
            ContentValues datanya = new ContentValues();
            datanya.put("nama",nm);
            datanya.put("nohp",hp);
            dbku.update("kontak",datanya,"nama='"+nm+"'",null);
            listKontak.removeIf(kontak -> kontak.banding(nm)==1);
            kontak newKontak = new kontak(nm,hp);
            Kontakadapter.add(newKontak);
            Toast.makeText(getApplicationContext(), "Kontak Diupdate", Toast.LENGTH_SHORT).show();
        }
    }
    private void search_item(String nm, String hp)
    {
        Cursor cur = dbku.rawQuery("select * from kontak where nama='" + nm.trim() + "'", null);
        if (cur.getCount() > 0) {
            ContentValues datanya = new ContentValues();
            datanya.put("nama",nm);
            datanya.put("nohp",hp);
            listKontak.clear();
            kontak newKontak = new kontak(nm,hp);
            Kontakadapter.add(newKontak);
            Toast.makeText(getApplicationContext(), "Kontak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
    private void insertKontak(String nm, String hp){
        kontak newKontak = new kontak(nm,hp);
        Kontakadapter.add(newKontak);
    }

    @SuppressLint("Range")
    private void ambildata(){
        Cursor cur = dbku.rawQuery("select * from kontak",null);
        Toast.makeText(this,"Terdapat sejumlah " + cur.getCount(),
                Toast.LENGTH_LONG).show();
        int i=0;if(cur.getCount() > 0) cur.moveToFirst();
        while(i<cur.getCount()){
            insertKontak(cur.getString(cur.getColumnIndex("nama")),
                    cur.getString(cur.getColumnIndex("nohp")));
            cur.moveToNext();
            i++;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void operasi (View v){
            change_data(v);
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void change_data(View v){
        listKontak.clear();
        ambildata();
        LayoutInflater li = LayoutInflater.from(this);
        View inputDialog = li.inflate(R.layout.input_dialog,null);

        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setView(inputDialog);

        final EditText nama = inputDialog.findViewById(R.id.nama);
        final EditText noHp = inputDialog.findViewById(R.id.noHp);

        buat
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, which) -> {
                    if (v.getId() == R.id.btnAdd) {
                        add_item(nama.getText().toString(), noHp.getText().toString());
                    }
                    else if (v.getId() == R.id.btnEdit) {
                        edit_item(nama.getText().toString(), noHp.getText().toString());
                    }
                    else if (v.getId() == R.id.btnDelete) {
                        delete_item(nama.getText().toString(), noHp.getText().toString());
                    }
                    else if (v.getId() == R.id.btnSearch) {
                        search_item(nama.getText().toString(), noHp.getText().toString());
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("NO", (dialog, which) -> dialog.cancel());
        buat.show();
    }
}


