package com.perpus.views.auth;

import com.perpus.model.Anggota;
import com.perpus.model.DataStore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
 File        : RegisterView.java
 Deskripsi   : Tampilan dan logika registrasi akun anggota baru dalam sistem
               perpustakaan. Data tersimpan ke dalam DataStore.
 ============================================================================
*/

public class RegisterView extends StackPane {

    /**
     * FUNGSI: Menyediakan UI registrasi anggota baru dan menyimpan data ke dalam sistem.
     * @param rootLayout referensi root utama untuk berpindah kembali ke halaman login.
     */
    public RegisterView(BorderPane rootLayout) {
        getStyleClass().add("login-root");

        // ======== KARTU REGISTRASI ========
        VBox card = new VBox();
        card.getStyleClass().add("login-card");
        card.setSpacing(15);
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER_LEFT);

        // ======== HEADER ========
        Label title = new Label("Create a New Account");
        title.setFont(Font.font("Arial", 22));
        title.setStyle("-fx-font-weight: bold;");

        // ======== FIELD INPUT ========
        Label idLabel = new Label("ID Anggota:");
        TextField idField = new TextField();
        idField.setPromptText("ID Anggota");
        idField.getStyleClass().add("input-field");

        Label namaLabel = new Label("Username Anggota:");
        TextField namaField = new TextField();
        namaField.setPromptText("Username Anggota");
        namaField.getStyleClass().add("input-field");

        Label alamatLabel = new Label("Alamat:");
        TextField alamatField = new TextField();
        alamatField.setPromptText("Alamat");
        alamatField.getStyleClass().add("input-field");

        Label hpLabel = new Label("Nomor HP:");
        TextField hpField = new TextField();
        hpField.setPromptText("Nomor HP");
        hpField.getStyleClass().add("input-field");

        // ======== PESAN STATUS REGISTRASI ========
        Label message = new Label();
        message.setStyle("-fx-text-fill: red;");

        // ======== TOMBOL REGISTER & LOGIN ========
        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().add("button-login");

        Button toLoginBtn = new Button("Already have an account? Login");
        toLoginBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #555;");

        // ======== AKSI REGISTRASI ========
        registerBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String alamat = alamatField.getText().trim();
            String hp = hpField.getText().trim();

            // Validasi input
            if (id.isEmpty() || nama.isEmpty() || alamat.isEmpty() || hp.isEmpty()) {
                message.setText("Semua field harus diisi.");
                return;
            }

            // Cek apakah ID sudah terdaftar
            boolean exists = DataStore.daftarAnggota.stream()
                    .anyMatch(anggota -> anggota.getId().equalsIgnoreCase(id));

            if (exists) {
                message.setText("ID sudah terdaftar.");
            } else {
                DataStore.daftarAnggota.add(new Anggota(id, nama, alamat, hp));
                message.setStyle("-fx-text-fill: green;");
                message.setText("Registrasi berhasil!");
            }
        });

        toLoginBtn.setOnAction(e -> rootLayout.setCenter(new LoginView(rootLayout)));

        // ======== SUSUN KOMPONEN KE TAMPILAN ========
        VBox wrapper = new VBox(
                title,
                idLabel, idField,
                namaLabel, namaField,
                alamatLabel, alamatField,
                hpLabel, hpField,
                message,
                registerBtn,
                toLoginBtn
        );
        wrapper.setSpacing(10);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().add(wrapper);
        VBox container = new VBox(card);
        container.setAlignment(Pos.CENTER);

        getChildren().add(container);
    }
}
