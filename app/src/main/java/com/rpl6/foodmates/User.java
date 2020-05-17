package com.rpl6.foodmates;

public class User {
    private int id;
    private String nama;
    private String email;
    private int umur;
    private String password;
    private String alamat;

    public User(int id, String nama, String email, String password, String alamat, int umur) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.umur = umur;
        this.alamat=alamat;
        this.password=password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

}
