package com.example.penjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

class Global{
    public static int ivar1=0;
    public static int ivar2=0;
    public static int opsi=0;
    public static double total=0;
    public static String name="";
}

public class MainActivity extends AppCompatActivity {
    EditText etNamaPelanggan, etNamaBarang, etJmlBarang, etHarga, etJmlUang;
    TextView tvNamaPembeli, tvNamaBarang, tvJmlBarang, tvHarga, tvUangBayar,
            tvTotal, tvKembalian, tvBonus, tvKeterangan;
    Button btnProses, btnHapus, btnKeluar ,btnDelete,btnEdit;

    //tambahan
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    String namaPelanggan, namaBarang, jumlahBarang, hargaBarang, uangBayar;
    double jmlBarang, hrgBarang, uangByr, total, kembalian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Aplikasi Penjualan");

        //EditText
        etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etJmlBarang = findViewById(R.id.etJmlBarang);
        etHarga = findViewById(R.id.etHarga);
        etJmlUang = findViewById(R.id.etJmlUang);

        //TextView
        tvNamaPembeli = findViewById(R.id.tvNamaPembeli);
        tvNamaBarang = findViewById(R.id.tvNamaBarang);
        tvJmlBarang = findViewById(R.id.tvJmlBarang);
        tvHarga = findViewById(R.id.tvHarga);
        tvUangBayar = findViewById(R.id.tvUangBayar);
        tvTotal = findViewById(R.id.tvTotal);
        tvKembalian = findViewById(R.id.tvKembalian);
        tvBonus = findViewById(R.id.tvBonus);
        tvKeterangan = findViewById(R.id.tvKeterangan);

        //Button
        btnProses = findViewById(R.id.btnProses);
        btnDelete = findViewById(R.id.btnDelete);
        btnHapus = findViewById(R.id.btnHapus);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnEdit = findViewById(R.id.btnEdit);

        btnProses.setOnClickListener(operasi);
        btnHapus.setOnClickListener(operasi);
        btnKeluar.setOnClickListener(operasi);
        btnEdit.setOnClickListener(operasi);
        btnDelete.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this,"db.sql",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {}
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists DBpenjualan(ID NUMERIC,namaBarang TEXT,jmlBarang TEXT,hrgBarang TEXT);");
    }
    @Override
    protected void onStop(){
        dbku.close();
        Opendb.close();
        super.onStop();

    }
    View.OnClickListener operasi = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            namaPelanggan = etNamaPelanggan.getText().toString().trim();
            namaBarang = etNamaBarang.getText().toString().trim();
            jumlahBarang = etJmlBarang.getText().toString().trim();
            hargaBarang = etHarga.getText().toString().trim();
            uangBayar = etJmlUang.getText().toString().trim();

            jmlBarang = Double.parseDouble(jumlahBarang);
            hrgBarang = Double.parseDouble(hargaBarang);
            uangByr = Double.parseDouble(uangBayar);
            tvNamaPembeli.setText("Nama Pembeli : " + namaPelanggan);
//        tvNamaBarang.setText("Nama Barang : " + namaBarang);
//        tvJmlBarang.setText("Jumlah Barang : " + jumlahBarang);
//        tvHarga.setText("Harga Barang : " + hargaBarang);
            tvUangBayar.setText("Uang bayar : " + uangBayar);

            switch (v.getId()){
                case R.id.btnProses:proses();closing();Global.opsi=1;break;
                case R.id.btnHapus:hapus();closing();break;
                case R.id.btnKeluar:keluar();closing();break;
                case R.id.btnDelete:delete();closing();Global.opsi=2;break;
                case R.id.btnEdit:edit();closing();Global.opsi=3;break;
            }
        }
    };
    @SuppressLint("Range")
    private void proses() {
        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from DBpenjualan where namaBarang='" + etNamaBarang.getText().toString().trim() + "'", null);
        if (cur.getCount() > 0) {
            Toast.makeText(this,"Data Sudah Ada,Mohon Edit Data",Toast.LENGTH_LONG).show();
        }
        else{
        Global.ivar1++;
        ContentValues dataku = new ContentValues();
        dataku.put("ID",Global.ivar1);
        dataku.put("namaBarang",namaBarang);
        dataku.put("jmlBarang",jmlBarang);
        dataku.put("hrgBarang",hrgBarang);
        dbku.insert("DBpenjualan",null,dataku);
        Toast.makeText(this,"Data Ditambahkan",Toast.LENGTH_LONG).show();
        }
    }
    private void hapus(){
        dbku.execSQL("delete from "+ "DBpenjualan");
        Global.ivar1=0;
        Global.ivar2=0;
        Global.name="";
        tvNamaPembeli.setText("");
        tvNamaBarang.setText("");
        tvJmlBarang.setText("");
        tvHarga.setText("");
        tvUangBayar.setText("");
        tvKembalian.setText("");
        tvKeterangan.setText("");
        tvBonus.setText("");
        tvTotal.setText("");
        Toast.makeText(getApplicationContext(), "Data sudah dihapus", Toast.LENGTH_SHORT).show();
    }
    private void keluar(){
        moveTaskToBack(true);
    }

    @SuppressLint("Range")
    private void delete(){
        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from DBpenjualan where namaBarang='" + etNamaBarang.getText().toString().trim() + "'", null);
        if (cur.getCount() > 0) {
        dbku.delete("DBpenjualan","namaBarang='"+etNamaBarang.getText().toString().trim()+"'",null);
        Toast.makeText(getApplicationContext(), "Item sudah dihapus", Toast.LENGTH_SHORT).show();
//        Global.ivar1--;
        }else{
            Toast.makeText(getApplicationContext(), "Item tidak ada", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("Range")
    private void edit(){
        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from DBpenjualan where namaBarang='" + etNamaBarang.getText().toString().trim() + "'", null);
        if (cur.getCount() > 0) {
        ContentValues dataku = new ContentValues();
        dataku.put("namaBarang",namaBarang);
        dataku.put("jmlBarang",jmlBarang);
        dataku.put("hrgBarang",hrgBarang);
        dbku.update("DBPenjualan",dataku,"namaBarang='"+etNamaBarang.getText().toString()+"'",null);
        Toast.makeText(getApplicationContext(), "Item sudah diedit", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Item tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"Range", "SetTextI18n"})
    private void closing(){
        Global.total=0;
        Global.name="";
        for(Global.ivar2=1;Global.ivar2<=Global.ivar1;Global.ivar2++) {
            @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from DBpenjualan where ID='" + Global.ivar2 + "'", null);

            if (cur.getCount() > 0) {
//                Toast.makeText(this, "Data Ditemukan Sejumlah " +
//                        cur.getCount(), Toast.LENGTH_LONG).show();
                cur.moveToFirst();
                Global.name=Global.name+
                        "Nama Barang : " + cur.getString(cur.getColumnIndex("namaBarang"))
                        +"\nJumlah Barang : " + cur.getString(cur.getColumnIndex("jmlBarang"))
                        +"\nHarga Barang : " + cur.getString(cur.getColumnIndex("hrgBarang"))+"\n";
                total = cur.getDouble(cur.getColumnIndex("jmlBarang")) * cur.getDouble(cur.getColumnIndex("hrgBarang"));
                Global.total=Global.total+total;
            } else {
//                Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
            }
        }
        tvNamaBarang.setText(Global.name);
        total=Global.total;
        tvTotal.setText("Total Belanja " + total);
        if (total >= 200000) {
            tvBonus.setText("Bonus : HardDisk 1TB");
        } else if (total >= 50000) {
            tvBonus.setText("Bonus : Keyboard Gaming");
        } else if (total >= 40000) {
            tvBonus.setText("Bonus : Mouse Gaming");
        } else {
            tvBonus.setText("Bonus : Tidak ada bonus!");
        }
        kembalian = (uangByr - total);
        if (uangByr < total) {
            tvKeterangan.setText("Keterangan : Uang bayar kurang Rp. " + (-kembalian));
            tvKembalian.setText("Uang Kembalian : Rp. 0");
        } else {
            tvKeterangan.setText("Keterangan : Tunggu kembalian");
            tvKembalian.setText("Uang Kembalian : Rp. " + kembalian);
        }
    }
}