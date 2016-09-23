package com.jemberonlineservice.studentdaily;

/**
 * Created by RenoNugraha on 9/7/2016.
 */
public class Pengguna {

    int id_pengguna;
    byte[] foto_pengguna;
    String nama_pengguna;
    String status_pengguna;
    String univ_pengguna;

    public Pengguna(){

    }

    public Pengguna(int id_pengguna, byte[] foto_pengguna, String nama_pengguna, String status_pengguna, String univ_pengguna){
        this.id_pengguna = id_pengguna;
        this.foto_pengguna = foto_pengguna;
        this.nama_pengguna = nama_pengguna;
        this.status_pengguna = status_pengguna;
        this.univ_pengguna = univ_pengguna;
    }

    public Pengguna(byte[] foto_pengguna, String nama_pengguna, String status_pengguna, String univ_pengguna){
        this.foto_pengguna = foto_pengguna;
        this.nama_pengguna = nama_pengguna;
        this.status_pengguna = status_pengguna;
        this.univ_pengguna = univ_pengguna;
    }

    public int getId_pengguna() {
        return this.id_pengguna;
    }

    public void setId_pengguna(int id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public byte[] getFoto_pengguna() {
        return this.foto_pengguna;
    }

    public void setFoto_pengguna(byte[] foto_pengguna) {
        this.foto_pengguna = foto_pengguna;
    }

    public String getNama_pengguna() {
        return this.nama_pengguna;
    }

    public void setNama_pengguna(String nama_pengguna) {
        this.nama_pengguna = nama_pengguna;
    }

    public String getStatus_pengguna() {
        return this.status_pengguna;
    }

    public void setStatus_pengguna(String status_pengguna) {
        this.status_pengguna = status_pengguna;
    }

    public String getUniv_pengguna() {
        return this.univ_pengguna;
    }

    public void setUniv_pengguna(String univ_pengguna) {
        this.univ_pengguna = univ_pengguna;
    }
}
