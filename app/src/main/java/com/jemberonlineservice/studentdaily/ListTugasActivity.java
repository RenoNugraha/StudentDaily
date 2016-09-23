package com.jemberonlineservice.studentdaily;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListTugasActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    DatabaseHandler databaseHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvtugas;
    private String selectedWord = null;
    private Cursor mCursor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tugas);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        lvtugas = (ListView) findViewById(R.id.list_tugas);
        databaseHandler = new DatabaseHandler(this, null, null, 1);
        TampilListTugas();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListTugasActivity.this, TugasActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void TampilListTugas(){
        try {
            Cursor cursor = databaseHandler.getALLTugas();
            if (cursor == null){
                Toast.makeText(this, "Gagal Mengambil Data",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (cursor.getCount() == 0){
                Toast.makeText(this, "Belum Ada Tugas",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String[] from = {
                    DatabaseHandler.KEY_NAMA_TUGAS,
                    DatabaseHandler.KEY_MATKUL,
                    DatabaseHandler.KEY_TGL_DEADLINE
            };
            int[] to = {
                    R.id.tv_tugas,
                    R.id.tv_tgmatkul,
                    R.id.tv_deadline
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.row2,
                    cursor,
                    from,
                    to,
                    0);
            lvtugas.setAdapter(simpleCursorAdapter);
            registerForContextMenu(lvtugas);
        } catch (Exception ex){
            String error = ex.getMessage();
            Toast.makeText(this, error,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_tugas){
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
        selectedWord = ((TextView) info.targetView.findViewById(R.id.tv_tugas)).getText().toString();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.listmenuclick);
        String menuItemName = menuItems[menuItemIndex];
        switch (menuItemName){
            case "Edit":
                Intent intent = new Intent(this, UpdateTugasActivity.class);
                intent.putExtra("isitugas", selectedWord);
                startActivity(intent);
                finish();
                return true;
            case "Hapus":
                databaseHandler.deleteTugas(selectedWord);
                mCursor = databaseHandler.getALLTugas();
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
                            databaseHandler.deleteAllTugas();
                            mCursor = databaseHandler.getALLTugas();
                            simpleCursorAdapter.swapCursor(mCursor);
                            dialog.dismiss();
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Belum Ada Tugas",
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
