package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText ax,bx,cx,r1x,r2x;
    private Button hitung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ax = (EditText) findViewById(R.id.id_ax);
        bx = (EditText) findViewById(R.id.id_bx);
        cx = (EditText) findViewById(R.id.id_cx);
        r1x = (EditText) findViewById(R.id.id_r1x);
        r2x = (EditText) findViewById(R.id.id_r2x);
        hitung = (Button) findViewById(R.id.id_hitung);

        hitung.setOnClickListener(new View.OnClickListener() {
            private float a,b,c,r1,r2;
            @Override
            public void onClick(View view) {
                a= Float.parseFloat(ax.getText().toString());
                b= Float.parseFloat(bx.getText().toString());
                c= Float.parseFloat(cx.getText().toString());

                if(Math.sqrt(b*b -4*a*c)>=0){
                    r1= (float) (-1*b + Math.sqrt(b*b -4*a*c))/(2*a);
                    r2= (float) (-1*b - Math.sqrt(b*b -4*a*c))/(2*a);
                    r1x.setText(Float.toString(r1));
                    r2x.setText(Float.toString(r2));
                }
                else{
                    r1x.setText("Hasil tidak ditemukan,nilai r1 imanjiner");
                    r2x.setText("Hasil tidak ditemukan,nilai r2 imanjiner");
                }



            }
        });

    }
}