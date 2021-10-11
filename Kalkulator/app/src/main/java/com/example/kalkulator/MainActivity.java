package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Bilangan1,Bilangan2;
    private TextView Hasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bilangan1=(EditText) findViewById(R.id.id_bil1);
        Bilangan2=(EditText) findViewById(R.id.id_bil2);
        Hasil=(TextView) findViewById(R.id.id_hasil);
    }
    public void operasi (View v){
        float bil1,bil2,hasil=0;
        Button button;
        bil1=Float.parseFloat(Bilangan1.getText().toString());
        bil2=Float.parseFloat(Bilangan2.getText().toString());
        switch (v.getId()){
            case R.id.buttonTambah: hasil=bil1+bil2;break;
            case R.id.buttonKurang: hasil=bil1-bil2;break;
            case R.id.buttonBagi: hasil=bil1/bil2;break;
            case R.id.buttonKali: hasil=bil1*bil2;break;
        }
        button=(Button) findViewById(v.getId());
        Hasil.setText(bil1+""+button.getText()+""+bil2+"="+hasil);
    }
}