package com.perpus.views.components;

import com.perpus.model.Anggota;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 File        : UpdateAnggota.java
 Deskripsi   : Form untuk memperbarui data anggota (kecuali ID) pada sistem
               perpustakaan. Data diperbarui langsung pada objek `Anggota`.
 ============================================================================
*/

public class UpdateAnggota {

    private final Stage modalStage;
    private final TextField idField;
    private final TextField namaField;
    private final TextField alamatField;
    private final TextField noHpField;

    private final Anggota anggota;

    /**
     * Konstruktor untuk menampilkan form update data anggota.
     * @param anggota objek anggota yang datanya akan diperbarui
     */
    public UpdateAnggota(Anggota anggota) {
        this.anggota = anggota;

        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Update Anggota");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("modal-container");

        // Judul
        Label title = new Label("Update Anggota");
        title.getStyleClass().add("modal-title");

        // Field ID (tidak dapat diubah)
        idField = new TextField(anggota.getId());
        idField.setPromptText("ID Anggota");
        idField.setDisable(true);

        // Field nama
        namaField = new TextField(anggota.getNama());
        namaField.setPromptText("Nama Anggota");

        // Field alamat
        alamatField = new TextField(anggota.getAlamat());
        alamatField.setPromptText("Alamat");

        // Field nomor HP
        noHpField = new TextField(anggota.getNoHp());
        noHpField.setPromptText("No HP");

        // Tombol Simpan
        Button btnSave = new Button("Simpan");
        btnSave.getStyleClass().add("modal-btn-save");
        btnSave.setOnAction(e -> handleSave());

        // Tombol Batal
        Button btnCancel = new Button("Batal");
        btnCancel.getStyleClass().add("modal-btn-cancel");
        btnCancel.setOnAction(e -> modalStage.close());

        HBox buttonBox = new HBox(10, btnSave, btnCancel);
        buttonBox.getStyleClass().add("modal-btn-box");

        layout.getChildren().addAll(
                title,
                idField,
                namaField,
                alamatField,
                noHpField,
                buttonBox
        );

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/styles/updateAnggota.css").toExternalForm());
        modalStage.setScene(scene);
    }

    /**
     * Menampilkan form update anggota dalam mode blocking (wait).
     */
    public void show() {
        modalStage.showAndWait();
    }

    /**
     * Menyimpan perubahan ke objek `Anggota` dan menutup modal.
     */
    private void handleSave() {
        anggota.setNama(namaField.getText().trim());
        anggota.setAlamat(alamatField.getText().trim());
        anggota.setNoHp(noHpField.getText().trim());

        System.out.println("Updated Anggota: " + anggota.getNama());
        modalStage.close();
    }
}
