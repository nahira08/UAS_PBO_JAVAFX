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
 File        : PeminjamanView.java
 Deskripsi   : Tampilan halaman peminjaman buku. Menampilkan daftar peminjaman
               yang sedang berlangsung dan menghitung denda jika terlambat.
 ============================================================================
*/

package com.perpus.views;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.perpus.model.Buku;
import com.perpus.model.DataStore;
import com.perpus.model.Peminjaman;
import com.perpus.views.auth.LoginView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Kelas ini merepresentasikan halaman peminjaman buku.
 * Digunakan untuk menampilkan semua data peminjaman dan melakukan pengembalian buku.
 */
public class PeminjamanView extends BorderPane {

    private final VBox sidebar;
    private final Label labelSapa;
    private final TableView<Peminjaman> tablePeminjaman;
    private final Label jumlahPeminjamanLabel;

    /**
     * Konstruktor untuk menampilkan tampilan PeminjamanView.
     */
    public PeminjamanView() {
        // Load CSS
        String cssPath = getClass().getResource("/styles/buku.css").toExternalForm();
        getStylesheets().add(cssPath);
        getStyleClass().add("root");

        // ==== SIDEBAR ====
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
        btnDaftarAnggota.getStyleClass().add("nonselect-btn");
        btnDaftarAnggota.setOnAction(e -> handleLihatAnggota());

        Button btnPeminjaman = new Button("🔄 Peminjaman");
        btnPeminjaman.getStyleClass().add("sidebar-btn"); // active

        Button btnLogout = new Button("🚪 Logout");
        btnLogout.getStyleClass().add("nonselect-btn");
        btnLogout.setOnAction(e -> handleLogout());

        sidebar.getChildren().addAll(titleLabel, btnDaftarBuku, btnDaftarAnggota, btnPeminjaman, btnLogout);
        setLeft(sidebar);

        // ==== MAIN CONTENT ====
        VBox contentArea = new VBox(20);
        contentArea.getStyleClass().add("content-area");
        contentArea.setAlignment(Pos.TOP_LEFT);
        contentArea.setPadding(new Insets(30));

        labelSapa = new Label("Hai, " + DataStore.namaLogin);
        labelSapa.getStyleClass().add("welcome-label");

        // Kartu jumlah peminjaman
        jumlahPeminjamanLabel = new Label();
        jumlahPeminjamanLabel.getStyleClass().add("jumlah-anggota-text");

        Label jumlahLabel = new Label("Jumlah Peminjaman");
        jumlahLabel.getStyleClass().add("jumlah-anggota-label");

        VBox jumlahCard = new VBox(5, jumlahLabel, jumlahPeminjamanLabel);
        jumlahCard.getStyleClass().add("anggota-card");
        updateJumlahPeminjaman();

        // Table Peminjaman
        tablePeminjaman = new TableView<>();
        tablePeminjaman.getStyleClass().add("anggota-table");
        tablePeminjaman.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<Peminjaman, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdPeminjaman()));
        colId.setPrefWidth(80);

        TableColumn<Peminjaman, String> colIdAnggota = new TableColumn<>("ID Anggota");
        colIdAnggota.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdAnggota()));
        colIdAnggota.setPrefWidth(100);

        TableColumn<Peminjaman, String> colKodeBuku = new TableColumn<>("Kode Buku");
        colKodeBuku.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKodeBuku()));
        colKodeBuku.setPrefWidth(100);

        TableColumn<Peminjaman, String> colTanggalPinjam = new TableColumn<>("Tanggal Pinjam");
        colTanggalPinjam.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggalPinjam().toString()));
        colTanggalPinjam.setPrefWidth(120);

        TableColumn<Peminjaman, String> colTanggalKembali = new TableColumn<>("Tanggal Kembali");
        colTanggalKembali.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggalKembali().toString()));
        colTanggalKembali.setPrefWidth(120);

        TableColumn<Peminjaman, String> colDenda = new TableColumn<>("Denda");
        colDenda.setCellValueFactory(data -> {
            long terlambat = ChronoUnit.DAYS.between(data.getValue().getTanggalKembali(), LocalDate.now());
            long denda = terlambat > 0 ? terlambat * 2000 : 0;
            return new SimpleStringProperty("Rp " + denda);
        });
        colDenda.setPrefWidth(100);

        TableColumn<Peminjaman, Void> colAksi = new TableColumn<>("Action");
        colAksi.setPrefWidth(160);
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button kembalikanButton = new Button("Kembalikan");

            {
                kembalikanButton.getStyleClass().add("pinjam-btn");
                kembalikanButton.setOnAction(event -> {
                    Peminjaman pem = getTableView().getItems().get(getIndex());

                    // Update status buku
                    Buku buku = DataStore.daftarBuku.stream()
                            .filter(b -> b.getKode().equalsIgnoreCase(pem.getKodeBuku()))
                            .findFirst()
                            .orElse(null);

                    if (buku != null) buku.setTersedia(true);

                    // Hapus data peminjaman
                    DataStore.daftarPeminjaman.remove(pem);
                    tablePeminjaman.getItems().setAll(DataStore.daftarPeminjaman);
                    updateJumlahPeminjaman();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : kembalikanButton);
            }
        });

        tablePeminjaman.getColumns().addAll(
                colId,
                colIdAnggota,
                colKodeBuku,
                colTanggalPinjam,
                colTanggalKembali,
                colDenda,
                colAksi
        );

        tablePeminjaman.setItems(FXCollections.observableArrayList(DataStore.daftarPeminjaman));

        contentArea.getChildren().addAll(labelSapa, jumlahCard, tablePeminjaman);
        setCenter(contentArea);
    }

    /**
     * Mengupdate jumlah total peminjaman dan menampilkannya di UI.
     */
    private void updateJumlahPeminjaman() {
        jumlahPeminjamanLabel.setText(String.valueOf(DataStore.daftarPeminjaman.size()));
        jumlahPeminjamanLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FF59E9;");
    }

    /**
     * Navigasi ke halaman Daftar Buku.
     */
    private void handleLihatBuku() {
        BorderPane root = (BorderPane) this.getScene().getRoot();
        BukuView view = new BukuView();
        view.setNamaAnggota(DataStore.namaLogin);
        root.setCenter(view);
    }

    /**
     * Navigasi ke halaman Daftar Anggota.
     */
    private void handleLihatAnggota() {
        BorderPane root = (BorderPane) this.getScene().getRoot();
        com.perpus.views.anggota.AnggotaView view = new com.perpus.views.anggota.AnggotaView();
        view.setNamaAnggota(DataStore.namaLogin);
        root.setCenter(view);
    }

    /**
     * Logout dan kembali ke halaman Login.
     */
    private void handleLogout() {
        BorderPane root = (BorderPane) this.getScene().getRoot();
        root.setCenter(new LoginView(root));
    }

    /**
     * Setter untuk menampilkan nama pengguna di label sapaan.
     * @param nama nama pengguna
     */
    public void setNamaAnggota(String nama) {
        labelSapa.setText("Hai, " + nama);
    }
}
