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
 File        : Buku.java
 Deskripsi   : Kelas model untuk merepresentasikan data buku
               dalam sistem perpustakaan.
 ============================================================================
*/

/**
 * Kelas Buku merepresentasikan entitas buku yang tersedia
 * dalam sistem perpustakaan.
 * Menyimpan informasi terkait kode buku, judul, pengarang, tahun terbit, dan status ketersediaan.
 */
public class Buku {
    private String kode;         // Kode unik buku
    private String judul;        // Judul buku
    private String pengarang;    // Nama pengarang buku
    private int tahun;           // Tahun terbit buku
    private boolean tersedia;    // Status ketersediaan (true = tersedia)

    /**
     * Konstruktor untuk membuat objek Buku baru.
     *
     * @param kode       Kode buku
     * @param judul      Judul buku
     * @param pengarang  Pengarang buku
     * @param tahun      Tahun terbit
     * @param tersedia   Status ketersediaan
     */
    public Buku(String kode, String judul, String pengarang, int tahun, boolean tersedia) {
        this.kode = kode;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun = tahun;
        this.tersedia = tersedia;
    }

    // Getter dan Setter untuk semua atribut

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getPengarang() { return pengarang; }
    public void setPengarang(String pengarang) { this.pengarang = pengarang; }

    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }

    public boolean isTersedia() { return tersedia; }
    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
}
