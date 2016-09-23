package com.jemberonlineservice.studentdaily;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListJadwalActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    DatabaseHandler databaseHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvjadwal;
    private String selectedWord = null;
    private Cursor mCursor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jadwal);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        lvjadwal = (ListView) findViewById(R.id.list_jadwal);
        databaseHandler = new DatabaseHandler(this, null, null, 1);
        TampilListJadwal();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListJadwalActivity.this, JadwalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void TampilListJadwal(){
        try {
            Cursor cursor = databaseHandler.getALLJadwal();
            if (cursor == null){
                Toast.makeText(this, "Gagal Mengambil Data",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (cursor.getCount() == 0){
                Toast.makeText(this, "Belum Ada Jadwal",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String[] from = {
                    DatabaseHandler.KEY_NAMA_MATKUL,
                    DatabaseHandler.KEY_DOSEN_MATKUL,
                    DatabaseHandler.KEY_JAM_MATKUL
            };
            int[] to = {
                    R.id.tv_matkul,
                    R.id.tv_dosen,
                    R.id.tv_jam
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.row,
                    cursor,
                    from,
                    to,
                    0);
            lvjadwal.setAdapter(simpleCursorAdapter);
            registerForContextMenu(lvjadwal);
        } catch (Exception ex){
            String error = ex.getMessage();
            Toast.makeText(this, error,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_jadwal){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            String[] menuItems = getResources().getStringArray(R.array.listmenuclick);
            for (int i = 0; i<menuItems.length; i++){
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedWord = ((TextView) info.targetView.findViewById(R.id.tv_matkul)).getText().toString();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.listmenuclick);
        String menuItemName = menuItems[menuItemIndex];
        switch (menuItemName){
            case "Edit":
                Intent intent = new Intent(this, UpdateJadwalActivity.class);
                intent.putExtra("isijadwal", selectedWord);
                startActivity(intent);
                finish();
                return true;
            case "Hapus":
                databaseHandler.deleteJadwal(selectedWord);
                mCursor = databaseHandler.getALLJadwal();
                simpleCursorAdapter.swapCursor(mCursor);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_hpssemua:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.popup_delete);
                dialog.show();
                Button yes = (Button) dialog.findViewById(R.id.btn_yahapus);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            databaseHandler.deleteAllJadwal();
                            mCursor = databaseHandler.getALLJadwal();
                            simpleCursorAdapter.swapCursor(mCursor);
                            dialog.dismiss();
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Belum Ada Jadwal",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                Button no = (Button) dialog.findViewById(R.id.btn_tdkhapus);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
