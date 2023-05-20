package com.example.apppengajuansurat.model;

public class DataFormMhsAktif {
    private String id, nama, nim, ttl, jurusan, kelas, alamat, nama_ortu, pekerjaan, instansi, alamat_ortu
            ,tgl_kirim, status;

    public DataFormMhsAktif(){

    }

    public DataFormMhsAktif(String id, String nama, String nim, String ttl, String jurusan, String kelas, String alamat, String nama_ortu, String pekerjaan, String instansi, String alamat_ortu, String tgl_kirim, String status){
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.ttl = ttl;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.alamat = alamat;
        this.nama_ortu = nama_ortu;
        this.pekerjaan = pekerjaan;
        this.instansi = instansi;
        this.alamat_ortu = alamat_ortu;
        this.tgl_kirim = tgl_kirim;
        this.status = status;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_ortu() {
        return nama_ortu;
    }

    public void setNama_ortu(String nama_ortu) {
        this.nama_ortu = nama_ortu;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getAlamat_ortu() {
        return alamat_ortu;
    }

    public void setAlamat_ortu(String alamat_ortu) {
        this.alamat_ortu = alamat_ortu;
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
}
