package com.perpus.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

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
 File        : AdminDashboardController.java
 Deskripsi   : Controller utama untuk dashboard admin, menangani navigasi
               fitur seperti manajemen buku, edit profil, dan logout.
 ============================================================================
*/

/**
 * Controller untuk dashboard admin pada sistem perpustakaan.
 * Bertanggung jawab atas navigasi tampilan utama admin, termasuk membuka halaman buku.
 */
public class AdminDashboardController {

    // FITUR: Layout utama dashboard admin
    // FUNGSI: Menjadi tempat kontainer utama (tengah) untuk memuat halaman lainnya
    @FXML
    private BorderPane root;

    // FITUR: Inisialisasi awal dashboard
    // FUNGSI: Menyiapkan aksi atau data awal saat tampilan dashboard di-load (jika diperlukan)
    @FXML
    private void initialize() {
        // Optional: inisialisasi awal jika diperlukan
    }

    // FITUR: Navigasi ke manajemen buku
    // FUNGSI: Menampilkan halaman daftar buku di bagian tengah layout dashboard admin
    @FXML
    private void handleBukaManajemenBuku() {
        try {
            Parent bukuView = FXMLLoader.load(getClass().getResource("/com/perpus/daftarbuku.fxml"));
            root.setCenter(bukuView);
        } catch (IOException e) {
            System.err.println("Gagal membuka halaman daftar buku:");
            e.printStackTrace();
        }
    }

    // FITUR: Edit profil admin
    // FUNGSI: Placeholder untuk navigasi ke fitur pengaturan profil admin
    @FXML
    private void handleEditProfil() {
        System.out.println("Edit profil admin...");
    }

    // FITUR: Logout admin
    // FUNGSI: Mengakhiri sesi pengguna dan diarahkan ke halaman login
    @FXML
    private void handleLogout() {
        System.out.println("Logout admin...");
    }
}
