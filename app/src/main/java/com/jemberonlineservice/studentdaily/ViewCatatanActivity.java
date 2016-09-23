package com.jemberonlineservice.studentdaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ViewCatatanActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler databaseHandler;
    Button btnEdit;
    EditText txJudul, txIsi;
    String txIsiCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_catatan);

        databaseHandler = new DatabaseHandler(this, null, null, 1);

        btnEdit = (Button) findViewById(R.id.btn_simpan4);

        txJudul = (EditText) findViewById(R.id.txt_judul3);
        txIsi = (EditText) findViewById(R.id.txt_isictn3);

        btnEdit.setOnClickListener(this);

        txIsiCatatan = getIntent().getStringExtra("isicatatan");
        List<Catatan> catatanList = databaseHandler.getAllCatatans(txIsiCatatan);
        for (Catatan ctn : catatanList){
            txJudul.setText(ctn.getJdl_catatan());
            txIsi.setText(ctn.getIsi_catatan());
        }
    }

    public void onClick(View v) {
        if (v == btnEdit){
            txIsiCatatan = getIntent().getStringExtra("isicatatan");
            Intent intent = new Intent(this, UpdateCatatanActivity.class);
            intent.putExtra("isicatatan", txIsiCatatan);
            startActivity(intent);
            finish();
        }
    }
}
