package com.perpus.model;

import java.time.LocalDate;

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
 File        : Peminjaman.java
 Deskripsi   : Kelas model yang merepresentasikan entitas peminjaman buku
               oleh anggota perpustakaan, lengkap dengan informasi tanggal pinjam
               dan tanggal kembali.
 ============================================================================
*/

/**
 * Kelas Peminjaman digunakan untuk menyimpan informasi transaksi peminjaman buku
 * oleh anggota perpustakaan.
 */
public class Peminjaman {
    
    /** ID unik peminjaman */
    private String idPeminjaman;

    /** ID anggota yang meminjam */
    private String idAnggota;

    /** Kode buku yang dipinjam */
    private String kodeBuku;

    /** Tanggal buku dipinjam */
    private LocalDate tanggalPinjam;

    /** Tanggal buku harus dikembalikan */
    private LocalDate tanggalKembali;

    /**
     * Konstruktor untuk membuat objek peminjaman baru.
     * 
     * @param idPeminjaman     ID peminjaman
     * @param idAnggota        ID anggota peminjam
     * @param kodeBuku         Kode buku yang dipinjam
     * @param tanggalPinjam    Tanggal peminjaman
     * @param tanggalKembali   Tanggal pengembalian
     */
    public Peminjaman(String idPeminjaman, String idAnggota, String kodeBuku, LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.kodeBuku = kodeBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    // Getter dan Setter

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
}
