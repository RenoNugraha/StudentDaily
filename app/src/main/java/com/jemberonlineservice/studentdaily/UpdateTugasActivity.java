package com.jemberonlineservice.studentdaily;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class UpdateTugasActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner mtklspin;
    DatabaseHandler databaseHandler;
    CheckBox setAlarm;
    Button btnHari, btnJam, btnSimpan;
    EditText txHari, txJam, txTugas;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String txMatkul, txIsiTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tugas);

        databaseHandler = new DatabaseHandler(this, null, null, 1);

        setAlarm = (CheckBox) findViewById(R.id.chkAlarm2);

        btnHari = (Button) findViewById(R.id.btn_hari2);
        btnJam = (Button) findViewById(R.id.btn_jam2);
        btnSimpan = (Button) findViewById(R.id.btn_simpan2);

        txHari = (EditText) findViewById(R.id.TxtHari2);
        txJam = (EditText) findViewById(R.id.TxtJam2);
        txTugas = (EditText) findViewById(R.id.txt_namatgas);

        btnHari.setOnClickListener(this);
        btnJam.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);

        mtklspin = (Spinner) findViewById(R.id.matkul_spinner);

        txIsiTugas = getIntent().getStringExtra("isitugas");
        List<Tugas> tugasList = databaseHandler.getAllTugass(txIsiTugas);
        for (Tugas tgs : tugasList){
            txTugas.setText(tgs.getNama_tugas());
        }

        mtklspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txMatkul = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getNamaMatkul();
    }

    private void getNamaMatkul(){
        List<String> lables = databaseHandler.getAllJadwals();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mtklspin.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == btnHari){
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_WEEK);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener(){

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfWeek){
                            calendar.set(year, monthOfYear, dayOfWeek);

                            txHari.setText(new SimpleDateFormat("EEEE").format(calendar.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnJam){
            final Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                    txJam.setText(hourOfDay + ":" + minute);
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnSimpan){
            try {
                String txthari = txHari.getText().toString();
                String txtjam = txJam.getText().toString();
                String txtday = txthari + ", " + txtjam;
                txIsiTugas = getIntent().getStringExtra("isitugas");
                List<Tugas> tugasList = databaseHandler.getAllTugass(txIsiTugas);
                for (Tugas tgs : tugasList){
                    Tugas tg = new Tugas(Integer.parseInt(String.valueOf(tgs.getId_tugas())),
                            txMatkul, txTugas.getText().toString(), txtday);
                    if (databaseHandler.updateTugas(tg)){
                        txTugas.setText("");
                        txHari.setText("");
                        txJam.setText("");
                        Intent intent = new Intent(this, ListTugasActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        txTugas.setText("GAGAL");
                    }
                }
            } catch (Exception ex){
                String error = ex.getMessage();
                Toast.makeText(this, error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}
