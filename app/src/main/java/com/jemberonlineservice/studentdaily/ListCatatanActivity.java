package com.jemberonlineservice.studentdaily;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListCatatanActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    DatabaseHandler databaseHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvcatatan;
    private String selectedWord = null;
    private Cursor mCursor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_catatan);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        lvcatatan = (ListView) findViewById(R.id.list_catatan);
        databaseHandler = new DatabaseHandler(this, null, null, 1);
        TampilListCatatan();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListCatatanActivity.this, CatatanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void TampilListCatatan(){
        try {
            Cursor cursor = databaseHandler.getALLCatatan();
            if (cursor == null){
                Toast.makeText(this, "Gagal Mengambil Data",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (cursor.getCount() == 0){
                Toast.makeText(this, "Belum Ada Catatan",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String[] from = {
                    DatabaseHandler.KEY_JDL_CATATAN,
                    DatabaseHandler.KEY_TGL_DIBUAT
            };
            int[] to = {
                    R.id.tv_jdlcatatan,
                    R.id.tv_dibuat
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.row3,
                    cursor,
                    from,
                    to,
                    0);
            lvcatatan.setAdapter(simpleCursorAdapter);
            lvcatatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView judul = (TextView) view.findViewById(R.id.tv_jdlcatatan);
                    String txt = judul.getText().toString();
                    Intent intent = new Intent(ListCatatanActivity.this, ViewCatatanActivity.class);
                    intent.putExtra("isicatatan", txt);
                    startActivity(intent);
                    finish();
                }
            });
            registerForContextMenu(lvcatatan);
        } catch (Exception ex){
            String error = ex.getMessage();
            Toast.makeText(this, error,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_catatan){
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
        selectedWord = ((TextView) info.targetView.findViewById(R.id.tv_jdlcatatan)).getText().toString();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.listmenuclick);
        String menuItemName = menuItems[menuItemIndex];
        switch (menuItemName){
            case "Edit":
                Intent intent = new Intent(this, UpdateCatatanActivity.class);
                intent.putExtra("isicatatan", selectedWord);
                startActivity(intent);
                finish();
                return true;
            case "Hapus":
                databaseHandler.deleteCatatan(selectedWord);
                mCursor = databaseHandler.getALLCatatan();
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
                            databaseHandler.deleteAllCatatan();
                            mCursor = databaseHandler.getALLCatatan();
                            simpleCursorAdapter.swapCursor(mCursor);
                            dialog.dismiss();
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Belum Ada Catatan",
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
