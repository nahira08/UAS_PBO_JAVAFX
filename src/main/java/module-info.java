module com.perpus {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.perpus to javafx.fxml;
    opens com.perpus.controller to javafx.fxml;
    opens com.perpus.model to javafx.base;
    opens com.perpus.views.auth to javafx.fxml;
    opens com.perpus.views.admin to javafx.fxml;
    opens com.perpus.views.anggota to javafx.fxml;

    exports com.perpus;
    exports com.perpus.controller;
    exports com.perpus.model;
    exports com.perpus.views.auth;
    // // exports com.perpus.views.admin;
    // // exports com.perpus.views.anggota;
}

