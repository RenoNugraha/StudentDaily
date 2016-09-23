package com.jemberonlineservice.studentdaily;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by RenoNugraha on 8/6/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentDailyBase";
    private static final String TABLE_JADWAL = "Jadwal";
    private static final String TABLE_TUGAS = "Tugas";
    private static final String TABLE_CATATAN = "Catatan";
    private static final String TABLE_PENGGUNA = "Pengguna";

    //PENGGUNA
    public static final String KEY_ID_PENGGUNA = "_id";
    public static final String KEY_FOTO_PENGGUNA = "foto";
    public static final String KEY_NAMA_PENGGUNA = "nama";
    public static final String KEY_STATUS_PENGGUNA = "status";
    public static final String KEY_UNIV_PENGGUNA = "univ";
    public static final String KEY_TGL_AKUNDIBUAT = "tgl_dibuat";

    // JADWAL
    public static final String KEY_ID_MATKUL = "_id";
    public static final String KEY_NAMA_MATKUL = "nama_matkul";
    public static final String KEY_DOSEN_MATKUL = "dosen_matkul";
    public static final String KEY_JAM_MATKUL = "jam_matkul";

    // TUGAS
    public static final String KEY_ID_TUGAS = "_id";
    public static final String KEY_MATKUL = "matkul";
    public static final String KEY_NAMA_TUGAS = "nama_tugas";
    public static final String KEY_TGL_DEADLINE = "tgl_deadline";

    // CATATAN
    public static final String KEY_ID_CATATAN = "_id";
    public static final String KEY_JDL_CATATAN = "jdl_catatan";
    public static final String KEY_ISI_CATATAN = "isi_catatan";
    public static final String KEY_TGL_DIBUAT = "tgl_dibuat";

    public DatabaseHandler(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_JADWAL_TABLE = "CREATE TABLE " + TABLE_JADWAL + "("
                + KEY_ID_MATKUL + " INTEGER PRIMARY KEY," + KEY_NAMA_MATKUL + " TEXT,"
                + KEY_DOSEN_MATKUL + " TEXT," + KEY_JAM_MATKUL + " TEXT" + ")";
        db.execSQL(CREATE_JADWAL_TABLE);

        String CREATE_TUGAS_TABLE = "CREATE TABLE " + TABLE_TUGAS + "("
                + KEY_ID_TUGAS + " INTEGER PRIMARY KEY," + KEY_MATKUL + " TEXT,"
                + KEY_NAMA_TUGAS + " TEXT,"
                + KEY_TGL_DEADLINE + " TEXT" + ")";
        db.execSQL(CREATE_TUGAS_TABLE);

        String CREATE_CATATAN_TABLE = "CREATE TABLE " + TABLE_CATATAN + "("
                + KEY_ID_CATATAN + " INTEGER PRIMARY KEY," + KEY_JDL_CATATAN + " TEXT,"
                + KEY_ISI_CATATAN + " TEXT," + KEY_TGL_DIBUAT + " DATETIME" + ")";
        db.execSQL(CREATE_CATATAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JADWAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUGAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATATAN);
        onCreate(db);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // CRUD
    // JADWAL
    public Cursor getALLJadwal(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JADWAL, new String[] {KEY_ID_MATKUL,
                KEY_NAMA_MATKUL, KEY_DOSEN_MATKUL, KEY_JAM_MATKUL}, null, null,
                null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            db.close();
            return cursor;
        } else {
            db.close();
            return null;
        }
    }

    public List<String> getAllJadwals(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_JADWAL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public List<Jadwal> getAllJadwalss(String isijadwal){
        List<Jadwal> jadwalList = new ArrayList<Jadwal>();
        String selectQuery = "SELECT * FROM " + TABLE_JADWAL + " WHERE " + KEY_NAMA_MATKUL +
                " = \"" + isijadwal + "\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            Jadwal jadwal = new Jadwal();
            jadwal.setId_matkul(Integer.parseInt(cursor.getString(0)));
            jadwal.setNama_matkul(cursor.getString(1));
            jadwal.setDosen_matkul(cursor.getString(2));
            jadwal.setJam_matkul(cursor.getString(3));
            jadwalList.add(jadwal);

        }

        return jadwalList;
    }

    public void addJadwal(Jadwal jadwal){
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_MATKUL, jadwal.getNama_matkul());
        values.put(KEY_DOSEN_MATKUL, jadwal.getDosen_matkul());
        values.put(KEY_JAM_MATKUL, jadwal.getJam_matkul());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_JADWAL, null, values);
        db.close();
    }

    public boolean deleteJadwal(String namamatkul){
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_JADWAL + " WHERE " + KEY_NAMA_MATKUL
                + " = \"" + namamatkul + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        Jadwal jw = new Jadwal();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            jw.setId_matkul(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_JADWAL, KEY_ID_MATKUL + " = ? ", new String[] {
                    String.valueOf(jw.getId_matkul())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public String ReadDB(String selectedItem) {
        String nmadosen;

        String selectQuery = "SELECT " + KEY_DOSEN_MATKUL + " FROM " + TABLE_JADWAL + " WHERE " +
                KEY_NAMA_MATKUL + " = '" + selectedItem + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            nmadosen = c.getString(c.getColumnIndex(KEY_DOSEN_MATKUL));
            db.close();
            return nmadosen;
        } else {
            db.close();
            return null;
        }
    }

    public boolean updateJadwal(Jadwal jw){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_MATKUL, jw.getNama_matkul());
        values.put(KEY_DOSEN_MATKUL, jw.getDosen_matkul());
        values.put(KEY_JAM_MATKUL, jw.getJam_matkul());

        return db.update(TABLE_JADWAL, values, KEY_ID_MATKUL + " = ?",
                new String[] {String.valueOf(jw.getId_matkul())}) > 0;
    }

    public void deleteAllJadwal(){
        String q = "DELETE FROM " + TABLE_JADWAL;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(q);
        db.close();
    }

    // TUGAS
    public Cursor getALLTugas(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TUGAS, new String[] {KEY_ID_TUGAS,
                        KEY_MATKUL, KEY_NAMA_TUGAS, KEY_TGL_DEADLINE}, null, null,
                null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            db.close();
            return cursor;
        } else {
            db.close();
            return null;
        }
    }

    public List<Tugas> getAllTugass(String isitugas){
        List<Tugas> tugasList = new ArrayList<Tugas>();
        String selectQuery = "SELECT * FROM " + TABLE_TUGAS + " WHERE " + KEY_NAMA_TUGAS +
                " = \"" + isitugas + "\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            Tugas tugas = new Tugas();
            tugas.setId_tugas(Integer.parseInt(cursor.getString(0)));
            tugas.setNama_matkul(cursor.getString(1));
            tugas.setNama_tugas(cursor.getString(2));
            tugas.setTgl_deadline(cursor.getString(3));
            tugasList.add(tugas);
        }

        return tugasList;
    }

    public void addTugas(Tugas tugas){
        ContentValues values = new ContentValues();
        values.put(KEY_MATKUL, tugas.getNama_matkul());
        values.put(KEY_NAMA_TUGAS, tugas.getNama_tugas());
        values.put(KEY_TGL_DEADLINE, tugas.getTgl_deadline());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TUGAS, null, values);
        db.close();
    }

    public boolean deleteTugas(String namatugas){
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_TUGAS + " WHERE " + KEY_NAMA_TUGAS
                + " = \"" + namatugas + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        Tugas tg = new Tugas();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            tg.setId_tugas(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_TUGAS, KEY_ID_TUGAS + " = ? ", new String[] {
                    String.valueOf(tg.getId_tugas())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateTugas(Tugas tg){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATKUL, tg.getNama_matkul());
        values.put(KEY_NAMA_TUGAS, tg.getNama_tugas());
        values.put(KEY_TGL_DEADLINE, tg.getTgl_deadline());

        return db.update(TABLE_TUGAS, values, KEY_ID_TUGAS + " = ?",
                new String[] {String.valueOf(tg.getId_tugas())}) > 0;
    }

    public void deleteAllTugas(){
        String q = "DELETE FROM " + TABLE_TUGAS;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(q);
        db.close();
    }

    // CATATAN
    public Cursor getALLCatatan(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATATAN, new String[] {KEY_ID_CATATAN,
                        KEY_JDL_CATATAN, KEY_ISI_CATATAN, KEY_TGL_DIBUAT}, null, null,
                null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            db.close();
            return cursor;
        } else {
            db.close();
            return null;
        }
    }

    public List<Catatan> getAllCatatans(String isicatatan){
        List<Catatan> catatanList = new ArrayList<Catatan>();
        String selectQuery = "SELECT * FROM " + TABLE_CATATAN + " WHERE " + KEY_JDL_CATATAN +
                " = \"" + isicatatan + "\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            Catatan catatan = new Catatan();
            catatan.setId_catatan(Integer.parseInt(cursor.getString(0)));
            catatan.setJdl_catatan(cursor.getString(1));
            catatan.setIsi_catatan(cursor.getString(2));
            catatanList.add(catatan);
        }

        return catatanList;
    }

    public void addCatatan(Catatan catatan){
        ContentValues values = new ContentValues();
        values.put(KEY_JDL_CATATAN, catatan.getJdl_catatan());
        values.put(KEY_ISI_CATATAN, catatan.getIsi_catatan());
        values.put(KEY_TGL_DIBUAT, getDateTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CATATAN, null, values);
        db.close();
    }

    public boolean deleteCatatan(String jdlcatatan){
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_CATATAN + " WHERE " + KEY_JDL_CATATAN
                + " = \"" + jdlcatatan + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        Catatan ctn = new Catatan();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            ctn.setId_catatan(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_CATATAN, KEY_ID_CATATAN + " = ? ", new String[] {
                    String.valueOf(ctn.getId_catatan())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateCatatan(Catatan ctn){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JDL_CATATAN, ctn.getJdl_catatan());
        values.put(KEY_ISI_CATATAN, ctn.getIsi_catatan());

        return db.update(TABLE_CATATAN, values, KEY_ID_CATATAN + " = ?",
                new String[] {String.valueOf(ctn.getId_catatan())}) > 0;
    }

    public void deleteAllCatatan(){
        String q = "DELETE FROM " + TABLE_CATATAN;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(q);
        db.close();
    }

    // PENGGUNA
    public void addPengguna(Pengguna pengguna){
        ContentValues values = new ContentValues();
        values.put(KEY_FOTO_PENGGUNA,pengguna.foto_pengguna);
        values.put(KEY_NAMA_PENGGUNA, pengguna.nama_pengguna);
        values.put(KEY_STATUS_PENGGUNA, pengguna.status_pengguna);
        values.put(KEY_UNIV_PENGGUNA, pengguna.univ_pengguna);
        values.put(KEY_TGL_AKUNDIBUAT, getDateTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PENGGUNA, null, values);
        db.close();
    }

    Pengguna getPengguna(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PENGGUNA, new String[]{ KEY_ID_PENGGUNA,
        KEY_FOTO_PENGGUNA, KEY_NAMA_PENGGUNA, KEY_STATUS_PENGGUNA, KEY_UNIV_PENGGUNA,
        KEY_TGL_AKUNDIBUAT}, KEY_ID_PENGGUNA + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        Pengguna pengguna = new Pengguna(Integer.parseInt(cursor.getString(0)),
                cursor.getBlob(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));
        return pengguna;
    }

    public Cursor getALLPengguna(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PENGGUNA, new String[] {KEY_ID_PENGGUNA,
                        KEY_FOTO_PENGGUNA, KEY_NAMA_PENGGUNA,
                KEY_STATUS_PENGGUNA, KEY_UNIV_PENGGUNA, KEY_TGL_AKUNDIBUAT}, null, null,
                null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            db.close();
            return cursor;
        } else {
            db.close();
            return null;
        }
    }

    public int updatePengguna(Pengguna pengguna){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOTO_PENGGUNA, pengguna.foto_pengguna);
        values.put(KEY_NAMA_PENGGUNA, pengguna.nama_pengguna);
        values.put(KEY_STATUS_PENGGUNA, pengguna.status_pengguna);
        values.put(KEY_UNIV_PENGGUNA, pengguna.univ_pengguna);
        values.put(KEY_TGL_AKUNDIBUAT, getDateTime());
        return db.update(TABLE_PENGGUNA, values, KEY_ID_PENGGUNA + "=?",
                new String[]{String.valueOf(pengguna.getId_pengguna())});
    }

    public void deletePengguna(Pengguna pengguna){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PENGGUNA, KEY_ID_PENGGUNA + "=?",
                new String[]{String.valueOf(pengguna.getId_pengguna())});
        db.close();
    }
}
