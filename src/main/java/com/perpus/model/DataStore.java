package com.perpus.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
 File        : DataStore.java
 Deskripsi   : Kelas utilitas yang menyimpan data global aplikasi perpustakaan
               menggunakan struktur data ObservableList agar mudah di-bind ke JavaFX UI.
 ============================================================================
*/

/**
 * Kelas DataStore berfungsi sebagai tempat penyimpanan data global (shared state)
 * yang digunakan di seluruh aplikasi.
 * Menyimpan daftar buku, anggota, peminjaman, serta nama user yang sedang login.
 */
public class DataStore {

    /** Daftar seluruh buku yang terdaftar di sistem */
    public static ObservableList<Buku> daftarBuku = FXCollections.observableArrayList();

    /** Daftar anggota perpustakaan */
    public static ObservableList<Anggota> daftarAnggota = FXCollections.observableArrayList();

    /** Daftar peminjaman buku oleh anggota */
    public static ObservableList<Peminjaman> daftarPeminjaman = FXCollections.observableArrayList();

    /** Nama user yang sedang login saat ini */
    public static String namaLogin = "";
}
