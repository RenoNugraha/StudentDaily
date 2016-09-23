package com.jemberonlineservice.studentdaily;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JadwalActivity extends AppCompatActivity implements View.OnClickListener{

    CheckBox setAlarm;
    Button btnHari, btnJam, btnSimpan;
    EditText txHari, txJam, txMatkul, txDosen;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        setAlarm = (CheckBox) findViewById(R.id.chkAlarm);

        btnHari = (Button) findViewById(R.id.btn_hari);
        btnJam = (Button) findViewById(R.id.btn_jam);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        txHari = (EditText) findViewById(R.id.TxtHari);
        txJam = (EditText) findViewById(R.id.TxtJam);
        txMatkul = (EditText) findViewById(R.id.TxtMatkul);
        txDosen = (EditText) findViewById(R.id.TxtDosen);

        btnHari.setOnClickListener(this);
        btnJam.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
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
                DatabaseHandler db = new DatabaseHandler(this, null, null, 1);

                String txtmatkul = txMatkul.getText().toString();
                String txtdosen = txDosen.getText().toString();
                String txthari = txHari.getText().toString();
                String txtjam = txJam.getText().toString();
                String txtday = txthari + ", " + txtjam;

                db.addJadwal(new Jadwal(
                        txtmatkul,
                        txtdosen,
                        txtday
                ));
                txMatkul.setText("");
                txDosen.setText("");
                txHari.setText("");
                txJam.setText("");
                Intent intent = new Intent(this, ListJadwalActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e){
                txMatkul.setText("GAGAL");
            }

        }
    }
}
