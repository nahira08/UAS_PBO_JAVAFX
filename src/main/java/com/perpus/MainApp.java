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
 File        : MainApp.java
 Deskripsi   : Kelas utama untuk menjalankan aplikasi Sistem Perpustakaan.
               Inisialisasi data dummy dan menampilkan halaman login.
 ============================================================================
*/

package com.perpus;

import com.perpus.model.Anggota;
import com.perpus.model.Buku;
import com.perpus.model.DataStore;
import com.perpus.views.auth.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Kelas MainApp digunakan sebagai entry point dari aplikasi JavaFX.
 */
public class MainApp extends Application {

    /**
     * Layout utama yang digunakan untuk berpindah-pindah antar halaman.
     */
    public static BorderPane rootLayout;

    /**
     * Method utama yang dipanggil saat aplikasi dijalankan.
     * @param primaryStage stage utama JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        // ========== Tambahkan data dummy untuk testing awal ==========

        // Data anggota
        DataStore.daftarAnggota.add(new Anggota("A001", "Aini", "Melati", "081210002408"));
        DataStore.daftarAnggota.add(new Anggota("A002", "Alifia", "Melati", "0822333444"));
        DataStore.daftarAnggota.add(new Anggota("A003", "Marvel", "Melati", "08991234567"));
        DataStore.daftarAnggota.add(new Anggota("A004", "Jenita", "Melati", "08991234567"));

        // Data buku
        DataStore.daftarBuku.add(new Buku("B001", "Belajar Matematika Diskrit", "Nur Aini", 2015, true));
        DataStore.daftarBuku.add(new Buku("B002", "Belajar Aljabar Linear", "Fulan", 2015, true));
        DataStore.daftarBuku.add(new Buku("B003", "Belajar Bahasa Java", "Fulanah", 2015, true));

        // ========== Set up root layout ==========

        rootLayout = new BorderPane();

        // Tampilkan halaman login pertama kali
        rootLayout.setCenter(new LoginView(rootLayout));

        Scene scene = new Scene(rootLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());

        // ========== Atur properti stage utama ==========

        primaryStage.setTitle("Sistem Perpustakaan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Fungsi main untuk menjalankan aplikasi.
     * @param args argumen CLI (tidak digunakan).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
