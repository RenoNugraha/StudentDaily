package com.jemberonlineservice.studentdaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class UpdateCatatanActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler databaseHandler;
    Button btnSimpan;
    EditText txJudul, txIsi;
    String txIsiCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_catatan);

        databaseHandler = new DatabaseHandler(this, null, null, 1);

        btnSimpan = (Button) findViewById(R.id.btn_simpan4);

        txJudul = (EditText) findViewById(R.id.txt_judul2);
        txIsi = (EditText) findViewById(R.id.txt_isictn2);

        btnSimpan.setOnClickListener(this);

        txIsiCatatan = getIntent().getStringExtra("isicatatan");
        List<Catatan> catatanList = databaseHandler.getAllCatatans(txIsiCatatan);
        for (Catatan ctn : catatanList){
            txJudul.setText(ctn.getJdl_catatan());
            txIsi.setText(ctn.getIsi_catatan());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSimpan){
            try {
                String txtjudulctn = txJudul.getText().toString();
                String txtisictn = txIsi.getText().toString();

                txIsiCatatan = getIntent().getStringExtra("isicatatan");
                List<Catatan> catatanList = databaseHandler.getAllCatatans(txIsiCatatan);
                for (Catatan ctn : catatanList){
                    Catatan cn = new Catatan(Integer.parseInt(String.valueOf(ctn.getId_catatan())),
                            txtjudulctn, txtisictn);
                    if (databaseHandler.updateCatatan(cn)){
                        txJudul.setText("");
                        txIsi.setText("");
                        Intent intent = new Intent(this, ListCatatanActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        txJudul.setText("GAGAL");
                    }
                }
                Intent intent = new Intent(this, ListCatatanActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ex){
                String error = ex.getMessage();
                Toast.makeText(this, error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}
