package com.jemberonlineservice.studentdaily;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class UpdateJadwalActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler databaseHandler;
    CheckBox setAlarm;
    Button btnHari, btnJam, btnSimpan;
    EditText txHari, txJam, txMatkul, txDosen;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String txNamaMatkul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_jadwal);
        databaseHandler = new DatabaseHandler(this, null, null, 1);

        setAlarm = (CheckBox) findViewById(R.id.chkAlarm3);

        btnHari = (Button) findViewById(R.id.btn_hari3);
        btnJam = (Button) findViewById(R.id.btn_jam3);
        btnSimpan = (Button) findViewById(R.id.btn_simpan3);

        txHari = (EditText) findViewById(R.id.TxtHari3);
        txJam = (EditText) findViewById(R.id.TxtJam3);
        txMatkul = (EditText) findViewById(R.id.TxtMatkul2);
        txDosen = (EditText) findViewById(R.id.TxtDosen2);

        btnHari.setOnClickListener(this);
        btnJam.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);

        txNamaMatkul = getIntent().getStringExtra("isijadwal");
        List<Jadwal> jadwalList = databaseHandler.getAllJadwalss(txNamaMatkul);
        for (Jadwal jdw : jadwalList){
            txMatkul.setText(jdw.getNama_matkul());
            txDosen.setText(jdw.getDosen_matkul());
        }
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
                String txtmatkul = txMatkul.getText().toString();
                String txtdosen = txDosen.getText().toString();
                String txthari = txHari.getText().toString();
                String txtjam = txJam.getText().toString();
                String txtday = txthari + ", " + txtjam;
                txNamaMatkul = getIntent().getStringExtra("isijadwal");
                List<Jadwal> tugasList = databaseHandler.getAllJadwalss(txNamaMatkul);
                for (Jadwal jds : tugasList){
                    Jadwal jw = new Jadwal(Integer.parseInt(String.valueOf(jds.getId_matkul())),
                            txtmatkul, txtdosen, txtday);
                    if (databaseHandler.updateJadwal(jw)){
                        txMatkul.setText("");
                        txDosen.setText("");
                        txHari.setText("");
                        txJam.setText("");
                        Intent intent = new Intent(this, ListJadwalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        txMatkul.setText("GAGAL");
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
