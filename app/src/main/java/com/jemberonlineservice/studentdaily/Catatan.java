package com.jemberonlineservice.studentdaily;

import java.util.Calendar;

/**
 * Created by RenoNugraha on 8/17/2016.
 */
public class Catatan {
    int id_catatan;
    String jdl_catatan;
    String isi_catatan;

    public Catatan(){

    }

    public Catatan(int id_catatan, String jdl_catatan, String isi_catatan){
        this.id_catatan = id_catatan;
        this.jdl_catatan = jdl_catatan;
        this.isi_catatan = isi_catatan;
    }

    public Catatan(String jdl_catatan, String isi_catatan){
        this.jdl_catatan = jdl_catatan;
        this.isi_catatan = isi_catatan;
    }

    public int getId_catatan() {
        return this.id_catatan;
    }

    public void setId_catatan(int id_catatan) {
        this.id_catatan = id_catatan;
    }

    public String getJdl_catatan() {
        return this.jdl_catatan;
    }

    public void setJdl_catatan(String jdl_catatan) {
        this.jdl_catatan = jdl_catatan;
    }

    public String getIsi_catatan() {
        return this.isi_catatan;
    }

    public void setIsi_catatan(String isi_catatan) {
        this.isi_catatan = isi_catatan;
    }
}
