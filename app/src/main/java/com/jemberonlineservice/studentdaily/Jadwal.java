package com.jemberonlineservice.studentdaily;

/**
 * Created by RenoNugraha on 8/6/2016.
 */
public class Jadwal {
    int id_matkul;
    String nama_matkul;
    String dosen_matkul;
    String jam_matkul;

    public Jadwal(){

    }

    public Jadwal(int id_matkul, String nama_matkul, String dosen_matkul, String jam_matkul){
        this.id_matkul = id_matkul;
        this.nama_matkul = nama_matkul;
        this.dosen_matkul = dosen_matkul;
        this.jam_matkul = jam_matkul;
    }

    public Jadwal(String nama_matkul, String dosen_matkul, String jam_matkul){
        this.nama_matkul = nama_matkul;
        this.dosen_matkul = dosen_matkul;
        this.jam_matkul = jam_matkul;
    }

    public int getId_matkul(){
        return this.id_matkul;
    }

    public void setId_matkul(int id_matkul) {
        this.id_matkul = id_matkul;
    }

    public String getNama_matkul(){
        return this.nama_matkul;
    }

    public void setNama_matkul(String nama_matkul) {
        this.nama_matkul = nama_matkul;
    }

    public String getDosen_matkul() {
        return this.dosen_matkul;
    }

    public void setDosen_matkul(String dosen_matkul) {
        this.dosen_matkul = dosen_matkul;
    }

    public String getJam_matkul() {
        return this.jam_matkul;
    }

    public void setJam_matkul(String jam_matkul) {
        this.jam_matkul = jam_matkul;
    }
}
