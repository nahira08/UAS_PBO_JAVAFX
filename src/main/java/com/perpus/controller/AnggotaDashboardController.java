package com.perpus.controller;

import com.perpus.model.Anggota;
import com.perpus.model.DataStore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
 File        : AnggotaDashboardController.java
 Deskripsi   : Controller halaman dashboard untuk pengguna berperan sebagai 
               anggota perpustakaan. Menampilkan data anggota dan mengatur
               hak akses terbatas untuk tombol penghapusan data.
 ============================================================================
*/

/**
 * Controller untuk halaman dashboard anggota.
 * Bertugas menampilkan informasi anggota dan fitur akses terbatas berdasarkan user login.
 */
public class AnggotaDashboardController {

    // Fitur: Tabel daftar anggota
    @FXML private TableView<Anggota> tableAnggota;

    // Kolom-kolom data anggota
    @FXML private TableColumn<Anggota, String> colId;
    @FXML private TableColumn<Anggota, String> colNama;
    @FXML private TableColumn<Anggota, String> colAlamat;
    @FXML private TableColumn<Anggota, String> colNoHp;

    // Fitur: Kolom aksi untuk tombol Delete (akses terbatas)
    @FXML private TableColumn<Anggota, Void> colAksi;

    // Fitur: Label sapaan kepada pengguna yang login
    @FXML private Label labelSapa;

    /**
     * Inisialisasi awal controller, menghubungkan setiap kolom dengan properti dari objek Anggota.
     */
    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colNoHp.setCellValueFactory(new PropertyValueFactory<>("noHp"));

        // Meskipun kolom aksi tidak perlu property value, tetap dideklarasikan untuk keperluan penghapusan
        colAksi.setCellValueFactory(new PropertyValueFactory<>("Action"));

        // Menampilkan data anggota dari DataStore
        ObservableList<Anggota> data = FXCollections.observableArrayList(DataStore.daftarAnggota);
        tableAnggota.setItems(data);
    }

    /**
     * Menampilkan sapaan kepada anggota yang login.
     * Jika nama yang login adalah "Aini", maka tombol delete akan muncul.
     *
     * @param nama Nama anggota yang sedang login
     */
    public void setNamaAnggota(String nama) {
        if (labelSapa != null) {
            labelSapa.setText("Hai, " + nama);
        }

        // Hak akses tombol Delete hanya untuk Aini
        if ("Aini".equalsIgnoreCase(nama)) {
            addButtonDeleteToTable();
        }
    }

    /**
     * Menambahkan tombol Delete pada setiap baris di tabel anggota,
     * hanya aktif jika pengguna login adalah "Aini".
     * Tombol ini akan menghapus data dari tampilan dan dari DataStore.
     */
    private void addButtonDeleteToTable() {
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Delete");

            {
                btn.getStyleClass().add("delete-btn"); // CSS styling
                btn.setOnAction(event -> {
                    Anggota anggota = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(anggota);
                    DataStore.daftarAnggota.remove(anggota);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                // Tampilkan tombol hanya jika baris tidak kosong
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    /**
     * Handler tombol navigasi ke fitur daftar buku.
     */
    @FXML
    private void handleLihatBuku() {
        System.out.println("Lihat daftar buku oleh anggota...");
    }

    /**
     * Handler tombol navigasi ke fitur peminjaman buku.
     */
    @FXML
    private void handlePinjamBuku() {
        System.out.println("Fitur pinjam buku...");
    }

    /**
     * Handler tombol logout.
     */
    @FXML
    private void handleLogout() {
        System.out.println("Logout anggota...");
    }
}
