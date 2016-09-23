package com.jemberonlineservice.studentdaily;

/**
 * Created by RenoNugraha on 8/13/2016.
 */
public class Tugas {
    int id_tugas;
    String nama_matkul;
    String nama_tugas;
    String tgl_deadline;

    public Tugas(){

    }

    public Tugas(int id_tugas, String nama_matkul, String nama_tugas, String tgl_deadline){
        this.id_tugas = id_tugas;
        this.nama_matkul = nama_matkul;
        this.nama_tugas = nama_tugas;
        this.tgl_deadline = tgl_deadline;
    }

    public Tugas(String nama_matkul, String nama_tugas, String tgl_deadline){
        this.nama_matkul = nama_matkul;
        this.nama_tugas = nama_tugas;
        this.tgl_deadline = tgl_deadline;
    }

    public int getId_tugas(){
        return this.id_tugas;
    }

    public void setId_tugas(int id_tugas){
        this.id_tugas = id_tugas;
    }

    public String getNama_matkul(){
        return this.nama_matkul;
    }

    public void setNama_matkul(String nama_matkul){
        this.nama_matkul = nama_matkul;
    }

    public String getNama_tugas(){
        return this.nama_tugas;
    }

    public void setNama_tugas(String nama_tugas) {
        this.nama_tugas = nama_tugas;
    }

    public String getTgl_deadline() {
        return this.tgl_deadline;
    }

    public void setTgl_deadline(String tgl_deadline) {
        this.tgl_deadline = tgl_deadline;
    }
}
