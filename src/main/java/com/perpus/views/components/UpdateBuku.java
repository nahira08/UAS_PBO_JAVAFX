package com.perpus.views.components;

import com.perpus.model.Buku;

import javafx.geometry.Insets;
import javafx.scene.Scene;
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
 File        : UpdateBuku.java
 Deskripsi   : Form untuk mengubah data buku pada sistem perpustakaan.
               Setelah diedit, data dikembalikan melalui getter getUpdatedBuku().
 ============================================================================
*/

public class UpdateBuku extends Stage {

    private Buku updatedBuku;

    /**
     * Konstruktor untuk menampilkan form update buku.
     * @param buku data buku yang akan diubah.
     */
    public UpdateBuku(Buku buku) {
        setTitle("Update Buku");

        VBox container = new VBox(15);
        container.getStyleClass().add("modal-container");
        container.setPadding(new Insets(20));

        // ====== Judul Form ======
        Label title = new Label("Update Buku");
        title.getStyleClass().add("modal-title");

        // ====== Field Input ======
        TextField kodeField = new TextField(buku.getKode());
        kodeField.setPromptText("Kode Buku");
        kodeField.getStyleClass().add("text-field");

        TextField judulField = new TextField(buku.getJudul());
        judulField.setPromptText("Judul Buku");
        judulField.getStyleClass().add("text-field");

        TextField pengarangField = new TextField(buku.getPengarang());
        pengarangField.setPromptText("Pengarang");
        pengarangField.getStyleClass().add("text-field");

        TextField tahunField = new TextField(String.valueOf(buku.getTahun()));
        tahunField.setPromptText("Tahun Terbit");
        tahunField.getStyleClass().add("text-field");

        CheckBox tersediaCheck = new CheckBox("Tersedia");
        tersediaCheck.setSelected(buku.isTersedia());

        // ====== Tombol Aksi ======
        HBox btnBox = new HBox(10);
        btnBox.getStyleClass().add("modal-btn-box");

        Button simpanBtn = new Button("Simpan");
        simpanBtn.getStyleClass().add("modal-btn-save");

        Button batalBtn = new Button("Batal");
        batalBtn.getStyleClass().add("modal-btn-cancel");

        simpanBtn.setOnAction(e -> {
            updatedBuku = new Buku(
                    kodeField.getText().trim(),
                    judulField.getText().trim(),
                    pengarangField.getText().trim(),
                    Integer.parseInt(tahunField.getText().trim()),
                    tersediaCheck.isSelected()
            );
            System.out.println("Buku diupdate: " + updatedBuku.getJudul());
            close();
        });

        batalBtn.setOnAction(e -> {
            updatedBuku = null;
            close();
        });

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
     * Getter untuk mengambil data buku terbaru yang telah diedit.
     * @return Buku yang sudah diperbarui, atau null jika batal.
     */
    public Buku getUpdatedBuku() {
        return updatedBuku;
    }
}
