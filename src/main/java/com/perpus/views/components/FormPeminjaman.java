package com.perpus.views.components;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.perpus.model.Buku;
import com.perpus.model.DataStore;
import com.perpus.model.Peminjaman;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
 File        : FormPeminjaman.java
 Deskripsi   : Komponen modal (popup) untuk melakukan peminjaman buku. 
               Meliputi pemilihan tanggal pengembalian, validasi jumlah 
               pinjaman, serta penyimpanan data ke DataStore.
 ============================================================================
*/

public class FormPeminjaman extends Stage {

    private LocalDate tanggalKembali;
    private boolean isConfirmed = false;

    /**
     * Konstruktor untuk memunculkan form peminjaman buku.
     * @param owner jendela utama yang membuka popup
     * @param buku buku yang akan dipinjam
     */
    public FormPeminjaman(Stage owner, Buku buku) {
        setTitle("Form Peminjaman");

        if (owner != null) {
            initOwner(owner);
        }
        initModality(Modality.APPLICATION_MODAL);

        // ======== ROOT CONTAINER ========
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getStyleClass().add("modal-container");

        // ======== HEADER ========
        Label titleLabel = new Label("Form Peminjaman");
        titleLabel.getStyleClass().add("modal-title");

        // ======== TANGGAL PENGEMBALIAN ========
        Label lblTglKembali = new Label("Tanggal Pengembalian:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Pilih tanggal");
        datePicker.getStyleClass().add("date-picker");
        datePicker.setEditable(false);

        // ======== ERROR MESSAGE ========
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px;");

        // ======== TOMBOL SIMPAN / BATAL ========
        Button btnSave = new Button("Simpan");
        btnSave.getStyleClass().add("modal-btn-save");

        Button btnCancel = new Button("Batal");
        btnCancel.getStyleClass().add("modal-btn-cancel");

        HBox btnBox = new HBox(10, btnCancel, btnSave);
        btnBox.getStyleClass().add("modal-btn-box");

        // Tambahkan ke root
        root.getChildren().addAll(
            titleLabel,
            lblTglKembali,
            datePicker,
            errorLabel,
            btnBox
        );

        // ======== AKSI TOMBOL ========
        btnCancel.setOnAction(e -> close());

        btnSave.setOnAction(e -> {
            LocalDate tglKembali = datePicker.getValue();

            if (tglKembali == null) {
                errorLabel.setText("Tanggal pengembalian wajib diisi!");
                return;
            }

            if (tglKembali.isBefore(LocalDate.now())) {
                errorLabel.setText("Tanggal pengembalian tidak boleh sebelum hari ini!");
                return;
            }

            // Validasi maksimal 3 buku per user
            List<Peminjaman> pinjamanUser = DataStore.daftarPeminjaman.stream()
                .filter(p -> p.getIdAnggota().equalsIgnoreCase(DataStore.namaLogin))
                .collect(Collectors.toList());

            if (pinjamanUser.size() >= 3) {
                errorLabel.setText("Maksimal peminjaman 3 buku per user!");
                return;
            }

            // Update status buku dan buat objek peminjaman
            buku.setTersedia(false);
            String idPeminjaman = "PMJ-" + (DataStore.daftarPeminjaman.size() + 1);

            Peminjaman peminjaman = new Peminjaman(
                idPeminjaman,
                DataStore.namaLogin,
                buku.getKode(),
                LocalDate.now(),
                tglKembali
            );

            DataStore.daftarPeminjaman.add(peminjaman);
            this.tanggalKembali = tglKembali;
            this.isConfirmed = true;
            close();
        });

        // ======== ATUR SCENE & CSS ========
        Scene scene = new Scene(root);
        String cssPath = getClass().getResource("/styles/formPinjam.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        setScene(scene);
    }

    /**
     * Getter tanggal kembali yang dipilih.
     */
    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    /**
     * Cek apakah tombol simpan ditekan.
     */
    public boolean isConfirmed() {
        return isConfirmed;
    }
}
