package com.perpus.views.anggota;

import com.perpus.model.Anggota;
import com.perpus.model.DataStore;
import com.perpus.views.BukuView;
import com.perpus.views.PeminjamanView;
import com.perpus.views.auth.LoginView;
import com.perpus.views.components.UpdateAnggota;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
 File        : AnggotaView.java
 Deskripsi   : Tampilan dashboard untuk anggota yang memuat sidebar navigasi
               dan daftar anggota dalam bentuk tabel.
 ============================================================================
*/

public class AnggotaView extends BorderPane {

    private final VBox sidebar;
    private final Label labelSapa;
    private final TableView<Anggota> tableAnggota;
    private String loggedInUser = null;

    /**
     * KONSTRUKTOR UTAMA
     * FITUR: Membuat tampilan UI untuk dashboard anggota
     * FUNGSI: Menampilkan sidebar dan area utama berisi informasi anggota
     */
    public AnggotaView() {
        // Load file CSS untuk styling tampilan
        String cssPath = getClass().getResource("/styles/anggota.css").toExternalForm();
        getStylesheets().add(cssPath);

        // Styling root
        getStyleClass().add("root");

        // SIDEBAR NAVIGASI
        sidebar = new VBox(20);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(30));
        sidebar.setMinWidth(200);

        Label titleLabel = new Label("📖 Smart Library");
        titleLabel.getStyleClass().add("sidebar-title");

        Button btnDaftarBuku = new Button("📚 Daftar Buku");
        btnDaftarBuku.getStyleClass().add("nonselect-btn");
        btnDaftarBuku.setOnAction(e -> handleLihatBuku());

        Button btnDaftarAnggota = new Button("🧑‍🦰 Daftar Anggota");
        btnDaftarAnggota.getStyleClass().add("sidebar-btn");

        Button btnPeminjaman = new Button("🔄 Peminjaman");
        btnPeminjaman.getStyleClass().add("nonselect-btn");
        btnPeminjaman.setOnAction(e -> handlePinjamBuku());

        Button btnLogout = new Button("🚪 Logout");
        btnLogout.getStyleClass().add("nonselect-btn");
        btnLogout.setOnAction(e -> handleLogout());

        sidebar.getChildren().addAll(titleLabel, btnDaftarBuku, btnDaftarAnggota, btnPeminjaman, btnLogout);
        setLeft(sidebar);

        // AREA KONTEN UTAMA
        VBox contentArea = new VBox(20);
        contentArea.getStyleClass().add("content-area");
        contentArea.setAlignment(Pos.TOP_LEFT);
        contentArea.setPadding(new Insets(30));

        labelSapa = new Label("Hai, User");
        labelSapa.getStyleClass().add("welcome-label");

        // Inisialisasi tabel anggota
        tableAnggota = new TableView<>();
        tableAnggota.getStyleClass().add("anggota-table");
        tableAnggota.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Tambah kolom ID
        TableColumn<Anggota, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colId.setPrefWidth(150);

        // Tambah kolom Nama
        TableColumn<Anggota, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        colNama.setPrefWidth(250);

        // Tambah kolom Alamat
        TableColumn<Anggota, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlamat()));
        colAlamat.setPrefWidth(250);

        // Tambah kolom No HP
        TableColumn<Anggota, String> colNoHp = new TableColumn<>("No HP");
        colNoHp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNoHp()));
        colNoHp.setPrefWidth(250);

        tableAnggota.getColumns().addAll(colId, colNama, colAlamat, colNoHp);

        // Kartu jumlah anggota
        VBox card = new VBox(5);
        card.getStyleClass().add("anggota-card");
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER_LEFT);

        Label jumlahLabel = new Label("Jumlah Anggota");
        jumlahLabel.getStyleClass().add("anggota-card-title");

        Label totalAnggota = new Label(String.valueOf(DataStore.daftarAnggota.size()));
        totalAnggota.getStyleClass().add("anggota-card-count");

        card.getChildren().addAll(jumlahLabel, totalAnggota);
        contentArea.getChildren().addAll(labelSapa, card, tableAnggota);
        setCenter(contentArea);
    }

    /**
     * Menyambut anggota berdasarkan nama login dan menambahkan kolom aksi
     * jika user yang login adalah "Aini"
     * 
     * @param nama nama anggota yang login
     */
    public void setNamaAnggota(String nama) {
        labelSapa.setText("Hai, " + nama);
        this.loggedInUser = nama;
        DataStore.namaLogin = nama;

        ObservableList<Anggota> data = FXCollections.observableArrayList(DataStore.daftarAnggota);
        tableAnggota.setItems(data);

        if (loggedInUser.equalsIgnoreCase("Aini")) {
            addActionColumn();
        }
    }

    /**
     * Menambahkan kolom aksi (Delete dan Update) hanya jika user bernama Aini
     */
    private void addActionColumn() {
        TableColumn<Anggota, Void> colAksi = new TableColumn<>("Action");
        colAksi.setPrefWidth(200);

        colAksi.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.getStyleClass().add("delete-btn");
                updateButton.getStyleClass().add("update-btn");

                deleteButton.setOnAction(event -> {
                    Anggota anggota = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(anggota);
                    DataStore.daftarAnggota.remove(anggota);
                    System.out.println("Deleted: " + anggota.getNama());
                });

                updateButton.setOnAction(event -> {
                    Anggota anggota = getTableView().getItems().get(getIndex());
                    UpdateAnggota updateModal = new UpdateAnggota(anggota);
                    updateModal.show();
                    tableAnggota.refresh();
                });

                actionBox.getChildren().addAll(updateButton, deleteButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });

        if (!tableAnggota.getColumns().contains(colAksi)) {
            tableAnggota.getColumns().add(colAksi);
        }
    }

    // ----------------------- Navigasi Sidebar -----------------------

    private void handleLihatBuku() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        BukuView view = new BukuView();
        view.setNamaAnggota(loggedInUser);
        rootLayout.setCenter(view);
    }

    private void handlePinjamBuku() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        PeminjamanView peminjamanView = new PeminjamanView();
        peminjamanView.setNamaAnggota(DataStore.namaLogin);
        rootLayout.setCenter(peminjamanView);
    }

    private void handleLogout() {
        BorderPane rootLayout = (BorderPane) this.getScene().getRoot();
        rootLayout.setCenter(new LoginView(rootLayout));
        DataStore.namaLogin = "";
    }
}
