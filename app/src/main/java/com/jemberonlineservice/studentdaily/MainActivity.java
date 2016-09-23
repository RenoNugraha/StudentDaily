package com.jemberonlineservice.studentdaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void jadwalClick(View v){
        Intent intent = new Intent(this, ListJadwalActivity.class);
        startActivity(intent);
    }

    public void tugasClick(View v){
        Intent intent = new Intent(this, ListTugasActivity.class);
        startActivity(intent);
    }

    public void catatanClick(View v){
        Intent intent = new Intent(this, ListCatatanActivity.class);
        startActivity(intent);
    }

    public void tentangClick(View v){
        Intent intent = new Intent(this, TentangActivity.class);
        startActivity(intent);
    }
}
