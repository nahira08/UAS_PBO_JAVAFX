package com.perpus.views.auth;

import java.io.IOException;

import com.perpus.model.DataStore;
import com.perpus.views.anggota.AnggotaView;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
 File        : LoginView.java
 Deskripsi   : Tampilan dan logika login untuk pengguna sistem perpustakaan.
               Mendukung login untuk Admin maupun Anggota.
 ============================================================================
*/

public class LoginView extends StackPane {

    private final BorderPane rootLayout;
    TextField usernameField = new TextField(); // Field untuk input nama pengguna

    /**
     * FITUR   : Halaman Login
     * FUNGSI  : Menyediakan antarmuka login bagi pengguna (Admin/Akun Anggota)
     *           serta navigasi ke halaman dashboard yang sesuai.
     */
    public LoginView(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
        getStyleClass().add("login-root");

        // ======== KOMPONEN KARTU LOGIN ========
        VBox card = new VBox(15);
        card.getStyleClass().add("login-card");
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);
        card.setAlignment(Pos.TOP_LEFT);

        // ======== HEADER SELAMAT DATANG ========
        Label welcomeText = new Label("Welcome to");
        welcomeText.setFont(Font.font("Arial", 16));
        welcomeText.setStyle("-fx-text-fill: #444;");

        Label titleText = new Label("Smart Library");
        titleText.setFont(Font.font("Arial", 26));
        titleText.setStyle("-fx-font-weight: bold;");

        VBox titleBox = new VBox(2, welcomeText, titleText);
        titleBox.setAlignment(Pos.CENTER);

        // ======== FORM LOGIN ========
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.TOP_LEFT);

        Label idLabel = new Label("ID Anggota:");
        TextField idField = new TextField();
        idField.setPromptText("ID Anggota");
        idField.getStyleClass().add("input-field");

        Label usernameLabel = new Label("Username Anggota:");
        usernameField.setPromptText("Username Anggota");
        usernameField.getStyleClass().add("input-field");

        formBox.getChildren().addAll(idLabel, idField, usernameLabel, usernameField);

        // ======== PESAN VALIDASI LOGIN ========
        Label message = new Label();
        message.setStyle("-fx-text-fill: red;");

        // ======== TOMBOL LOGIN DAN REGISTER ========
        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("button-login");

        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().add("button-register");

        HBox buttons = new HBox(10, loginBtn, registerBtn);
        buttons.setAlignment(Pos.CENTER);

        // ======== EVENT HANDLER UNTUK LOGIN ========
        loginBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String username = usernameField.getText().trim();

            // Login khusus untuk admin
            if (id.equals("381") && username.equalsIgnoreCase("AdminAini")) {
                message.setText("Login sebagai Admin");
                message.setStyle("-fx-text-fill: green;");
                bukaHalamanDashboardAdmin();
                return;
            }

            // Login sebagai anggota
            boolean found = DataStore.daftarAnggota.stream()
                    .anyMatch(anggota -> anggota.getId().equalsIgnoreCase(id)
                            && anggota.getNama().equalsIgnoreCase(username));

            if (found) {
                DataStore.namaLogin = username;
                message.setText("Login sebagai Anggota");
                message.setStyle("-fx-text-fill: green;");
                bukaHalamanDashboardAnggota();
            } else {
                message.setText("ID atau Username salah");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        // ======== AKSI REGISTER AKUN ========
        registerBtn.setOnAction(e -> rootLayout.setCenter(new RegisterView(rootLayout)));

        // ======== MERAKIT SEMUA KOMPONEN LOGIN ========
        card.getChildren().addAll(titleBox, formBox, message, buttons);

        VBox wrapper = new VBox(card);
        wrapper.setAlignment(Pos.CENTER);
        getChildren().add(wrapper);
    }

    /**
     * FUNGSI: Navigasi ke tampilan Dashboard Admin setelah login sebagai admin.
     */
    private void bukaHalamanDashboardAdmin() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/com/perpus/views/admin/dashboard_admin.fxml"));
            rootLayout.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * FUNGSI: Navigasi ke tampilan Dashboard Anggota setelah login berhasil.
     */
    private void bukaHalamanDashboardAnggota() {
        AnggotaView view = new AnggotaView();
        view.setNamaAnggota(usernameField.getText().trim());
        rootLayout.setCenter(view);
    }
}
