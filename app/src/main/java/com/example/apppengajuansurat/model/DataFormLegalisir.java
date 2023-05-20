package com.example.apppengajuansurat.model;

public class DataFormLegalisir {

    private String id, nama, nim, ttl, jurusan, kelas ,tgl_kirim, status;

    public DataFormLegalisir(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTgl_kirim() {
        return tgl_kirim;
    }

    public void setTgl_kirim(String tgl_kirim) {
        this.tgl_kirim = tgl_kirim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataFormLegalisir(String id, String nama, String nim, String ttl, String jurusan, String kelas, String tgl_kirim, String status){
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.ttl = ttl;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.tgl_kirim = tgl_kirim;
        this.status = status;
    }
}
