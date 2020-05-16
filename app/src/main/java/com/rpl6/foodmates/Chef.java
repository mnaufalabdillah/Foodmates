package com.rpl6.foodmates;

public class Chef {
    private int id;
    private String nama;
    private String spesialisasi;
    private int umur;

    public Chef(int id, String nama, int umur, String spesialisasi) {
        this.id = id;
        this.nama = nama;
        this.umur = umur;
        this.spesialisasi = spesialisasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }
}
