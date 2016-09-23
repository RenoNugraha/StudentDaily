package com.jemberonlineservice.studentdaily;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CatatanActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHandler databaseHandler;
    Button btnSimpan;
    EditText txJudul, txIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

        databaseHandler = new DatabaseHandler(this, null, null, 1);

        btnSimpan = (Button) findViewById(R.id.btn_simpan3);

        txJudul = (EditText) findViewById(R.id.txt_judul);
        txIsi = (EditText) findViewById(R.id.txt_isictn);

        btnSimpan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSimpan){
            try {
                DatabaseHandler db = new DatabaseHandler(this, null, null, 1);

                String txtjudulctn = txJudul.getText().toString();
                String txtisictn = txIsi.getText().toString();

                db.addCatatan(new Catatan(
                        txtjudulctn,
                        txtisictn
                ));
                txJudul.setText("");
                txIsi.setText("");
                Intent intent = new Intent(this, ListCatatanActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e){
                txJudul.setText("GAGAL");
            }

        }
    }
}
