package com.rpl6.foodmates;

public class Chef {
    private int id;
    private String nama;
    private String spesialisasi;
    private int umur;
    private String skill1, skill2, skill3;

    public Chef(int id, String nama, String spesialisasi, int umur, String skill1, String skill2, String skill3) {
        this.id = id;
        this.nama = nama;
        this.spesialisasi = spesialisasi;
        this.umur = umur;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
    }

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

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }
}
