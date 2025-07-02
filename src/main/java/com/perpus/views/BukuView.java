package com.perpus.views;

import com.perpus.model.Buku;
import com.perpus.model.DataStore;
import com.perpus.views.auth.LoginView;
import com.perpus.views.components.FormPeminjaman;
import com.perpus.views.components.TambahBuku;
import com.perpus.views.components.UpdateBuku;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
 File        : BukuView.java
 Deskripsi   : Halaman utama untuk menampilkan daftar buku, termasuk fitur
               tambah, update, delete, dan peminjaman buku.
 ============================================================================
*/

public class BukuView extends BorderPane {

    private final VBox sidebar;
    private final Label labelSapa;
    private final TableView<Buku> tableBuku;
    private final Label jumlahBukuLabel;
    private final Button btnTambahBuku;

    /**
     * Konstruktor untuk inisialisasi dan membangun tampilan daftar buku.
     */
    public BukuView() {

        String cssPath = getClass().getResource("/styles/buku.css").toExternalForm();
        System.out.println("CSS LOADED: " + cssPath);
        getStylesheets().add(cssPath);
        getStyleClass().add("root");

        // Sidebar Navigasi
        sidebar = new VBox(20);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(30));
        sidebar.setMinWidth(200);

        Label titleLabel = new Label("📖 Smart Library");
        titleLabel.getStyleClass().add("sidebar-title");

        Button btnDaftarBuku = new Button("📚 Daftar Buku");
        btnDaftarBuku.getStyleClass().add("sidebar-btn"); // halaman aktif

        Button btnDaftarAnggota = new Button("🧑‍🦰 Daftar Anggota");
        btnDaftarAnggota.getStyleClass().add("nonselect-btn");
        btnDaftarAnggota.setOnAction(e -> handleLihatAnggota());

        Button btnPeminjaman = new Button("🔄 Peminjaman");
        btnPeminjaman.getStyleClass().add("nonselect-btn");
        btnPeminjaman.setOnAction(e -> handlePeminjaman());

        Button btnLogout = new Button("🚪 Logout");
        btnLogout.getStyleClass().add("nonselect-btn");
        btnLogout.setOnAction(e -> handleLogout());

        sidebar.getChildren().addAll(titleLabel, btnDaftarBuku, btnDaftarAnggota, btnPeminjaman, btnLogout);
        setLeft(sidebar);

        // Konten Utama
        VBox contentArea = new VBox(20);
        contentArea.getStyleClass().add("content-area");
        contentArea.setAlignment(Pos.TOP_LEFT);
        contentArea.setPadding(new Insets(30));

        labelSapa = new Label("Hai, " + DataStore.namaLogin);
        labelSapa.getStyleClass().add("welcome-label");

        // Kartu Jumlah Buku
        jumlahBukuLabel = new Label();
        jumlahBukuLabel.getStyleClass().add("jumlah-anggota-text");

        Label jumlahLabel = new Label("Jumlah Buku");
        jumlahLabel.getStyleClass().add("jumlah-anggota-label");

        VBox jumlahCard = new VBox(5, jumlahLabel, jumlahBukuLabel);
        jumlahCard.getStyleClass().add("anggota-card");
        updateJumlahBuku();

        // Tabel Buku
        tableBuku = new TableView<>();
        tableBuku.getStyleClass().add("anggota-table");
        tableBuku.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<Buku, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKode()));
        colKode.setPrefWidth(80);

        TableColumn<Buku, String> colJudul = new TableColumn<>("Judul");
        colJudul.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJudul()));
        colJudul.setPrefWidth(200);

        TableColumn<Buku, String> colPengarang = new TableColumn<>("Pengarang");
        colPengarang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPengarang()));
        colPengarang.setPrefWidth(150);

        TableColumn<Buku, String> colTahun = new TableColumn<>("Tahun");
        colTahun.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTahun())));
        colTahun.setPrefWidth(100);

        TableColumn<Buku, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isTersedia() ? "Tersedia" : "Tidak Tersedia"));
        colStatus.setPrefWidth(150);

        // Kolom Aksi: Pinjam, Update, Delete
        TableColumn<Buku, Void> colAksi = new TableColumn<>("Action");
        colAksi.setPrefWidth(300);
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button pinjamButton = new Button("Pinjam");
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionBox = new HBox(5, pinjamButton, updateButton, deleteButton);

            {
                pinjamButton.getStyleClass().add("pinjam-btn");
                pinjamButton.setOnAction(event -> {
                    Buku buku = getTableView().getItems().get(getIndex());
                    if (!buku.isTersedia()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Buku ini sedang tidak tersedia!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        return;
                    }
                    Stage ownerStage = (Stage) tableBuku.getScene().getWindow();
                    FormPeminjaman modal = new FormPeminjaman(ownerStage, buku);
                    modal.showAndWait();
                    if (modal.isConfirmed()) {
                        tableBuku.getItems().setAll(DataStore.daftarBuku);
                    }
                });

                updateButton.getStyleClass().add("update-btn");
                updateButton.setOnAction(event -> {
                    Buku buku = getTableView().getItems().get(getIndex());
                    UpdateBuku modal = new UpdateBuku(buku);
                    modal.showAndWait();
                    Buku updated = modal.getUpdatedBuku();
                    if (updated != null) {
                        int index = DataStore.daftarBuku.indexOf(buku);
                        DataStore.daftarBuku.set(index, updated);
                        getTableView().getItems().set(getIndex(), updated);
                        System.out.println("Buku berhasil diupdate!");
                    }
                });

                deleteButton.getStyleClass().add("delete-btn");
                deleteButton.setOnAction(event -> {
                    Buku buku = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(buku);
                    DataStore.daftarBuku.remove(buku);
                    updateJumlahBuku();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    String user = DataStore.namaLogin;
                    if ("Aini".equalsIgnoreCase(user)) {
                        setGraphic(actionBox);
                    } else {
                        setGraphic(pinjamButton);
                    }
                }
            }
        });

        tableBuku.getColumns().addAll(colKode, colJudul, colPengarang, colTahun, colStatus, colAksi);
        ObservableList<Buku> data = FXCollections.observableArrayList(DataStore.daftarBuku);
        tableBuku.setItems(data);

        // Tombol Tambah Buku
        btnTambahBuku = new Button("Tambah Buku");
        btnTambahBuku.getStyleClass().add("tambah-btn");
        btnTambahBuku.setOnAction(e -> handleTambahBuku());

        contentArea.getChildren().addAll(labelSapa, jumlahCard, tableBuku);
        if ("Aini".equalsIgnoreCase(DataStore.namaLogin)) {
            contentArea.getChildren().add(btnTambahBuku);
        }

        setCenter(contentArea);
    }

    /**
     * Menampilkan modal tambah buku dan perbarui tampilan.
     */
    private void handleTambahBuku() {
        TambahBuku modal = new TambahBuku();
        modal.showAndWait();
        tableBuku.getItems().setAll(DataStore.daftarBuku);
        updateJumlahBuku();
    }

    /**
     * Memperbarui jumlah buku di tampilan.
     */
    private void updateJumlahBuku() {
        int jumlah = DataStore.daftarBuku.size();
        jumlahBukuLabel.setText(String.valueOf(jumlah));
        jumlahBukuLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FF59E9;");
    }

    /**
     * Mengatur ulang sapaan pengguna sesuai nama login.
     * @param nama nama pengguna
     */
    public void setNamaAnggota(String nama) {
        labelSapa.setText("Hai, " + nama);
    }

    /**
     * Navigasi ke halaman anggota.
     */
    private void handleLihatAnggota() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        com.perpus.views.anggota.AnggotaView anggotaView = new com.perpus.views.anggota.AnggotaView();
        anggotaView.setNamaAnggota(DataStore.namaLogin);
        rootLayout.setCenter(anggotaView);
    }

    /**
     * Navigasi ke halaman peminjaman.
     */
    private void handlePeminjaman() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        PeminjamanView peminjamanView = new PeminjamanView();
        peminjamanView.setNamaAnggota(DataStore.namaLogin);
        rootLayout.setCenter(peminjamanView);
    }

    /**
     * Logout dan kembali ke halaman login.
     */
    private void handleLogout() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        rootLayout.setCenter(new LoginView(rootLayout));
    }
}
