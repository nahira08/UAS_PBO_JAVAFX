package com.perpus.views.components;

import com.perpus.model.Buku;
import com.perpus.model.DataStore;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
 File        : TambahBuku.java
 Deskripsi   : Menyediakan tampilan popup untuk menambahkan buku baru ke 
               dalam sistem perpustakaan dengan menyimpan data ke DataStore.
 ============================================================================
*/

public class TambahBuku extends Stage {

    /**
     * Konstruktor menampilkan form tambah buku baru.
     */
    public TambahBuku() {
        setTitle("Tambah Buku");

        // === Root Container ===
        VBox container = new VBox(15);
        container.getStyleClass().add("modal-container");
        container.setPadding(new Insets(20));

        // === Title ===
        Label title = new Label("Tambah Buku");
        title.getStyleClass().add("modal-title");

        // === Input Fields ===
        TextField kodeField = new TextField();
        kodeField.setPromptText("Kode Buku");
        kodeField.getStyleClass().add("text-field");

        TextField judulField = new TextField();
        judulField.setPromptText("Judul Buku");
        judulField.getStyleClass().add("text-field");

        TextField pengarangField = new TextField();
        pengarangField.setPromptText("Pengarang");
        pengarangField.getStyleClass().add("text-field");

        TextField tahunField = new TextField();
        tahunField.setPromptText("Tahun Terbit");
        tahunField.getStyleClass().add("text-field");

        CheckBox tersediaCheck = new CheckBox("Tersedia");

        // === Tombol Aksi ===
        HBox btnBox = new HBox(10);
        btnBox.getStyleClass().add("modal-btn-box");

        Button simpanBtn = new Button("Simpan");
        simpanBtn.getStyleClass().add("modal-btn-save");

        Button batalBtn = new Button("Batal");
        batalBtn.getStyleClass().add("modal-btn-cancel");

        // === Aksi tombol Simpan ===
        simpanBtn.setOnAction(e -> {
            try {
                String kode = kodeField.getText().trim();
                String judul = judulField.getText().trim();
                String pengarang = pengarangField.getText().trim();
                int tahun = Integer.parseInt(tahunField.getText().trim());
                boolean tersedia = tersediaCheck.isSelected();

                if (kode.isEmpty() || judul.isEmpty() || pengarang.isEmpty()) {
                    showAlert("Semua field wajib diisi!");
                    return;
                }

                Buku buku = new Buku(kode, judul, pengarang, tahun, tersedia);
                DataStore.daftarBuku.add(buku);
                close();
            } catch (NumberFormatException ex) {
                showAlert("Tahun harus berupa angka!");
            }
        });

        // === Aksi tombol Batal ===
        batalBtn.setOnAction(e -> close());

        btnBox.getChildren().addAll(simpanBtn, batalBtn);

        container.getChildren().addAll(
                title,
                kodeField,
                judulField,
                pengarangField,
                tahunField,
                tersediaCheck,
                btnBox
        );

        Scene scene = new Scene(container);
        String css = getClass().getResource("/styles/manageBuku.css").toExternalForm();
        scene.getStylesheets().add(css);

        initModality(Modality.APPLICATION_MODAL);
        setScene(scene);
    }

    /**
     * Menampilkan alert kesalahan input.
     * @param message pesan yang ditampilkan ke pengguna
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Input Tidak Valid");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
