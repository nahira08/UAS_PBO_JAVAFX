package com.perpus.model;

/*
 ============================================================================
 DOKUMENTASI KODE - UAS PBO SISTEM PERPUSTAKAAN
 ----------------------------------------------------------------------------
 Nama Anggota Kelompok:
 - Nur Aini (202410370110381)
 - Alifia Nadia Ruksana (202410370110334)
 - Ayshea Marvella Pasha (202410370110379)
 - Jenita Oktaviana Ramadhani (202410370110338)
 ----------------------------------------------------------------------------
 File        : Anggota.java
 Deskripsi   : Kelas model untuk merepresentasikan data anggota
               dalam sistem perpustakaan.
 ============================================================================
*/

/**
 * Kelas Anggota merepresentasikan entitas anggota dalam sistem perpustakaan.
 * Menyimpan informasi berupa ID, nama, alamat, dan nomor HP.
 */
public class Anggota {
    private String id;       // ID unik anggota
    private String nama;     // Nama lengkap anggota
    private String alamat;   // Alamat tempat tinggal anggota
    private String noHp;     // Nomor handphone anggota

    /**
     * Konstruktor untuk membuat objek Anggota baru.
     * 
     * @param id     ID anggota
     * @param nama   Nama anggota
     * @param alamat Alamat anggota
     * @param noHp   Nomor HP anggota
     */
    public Anggota(String id, String nama, String alamat, String noHp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    // Getter dan Setter untuk masing-masing atribut

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
}
