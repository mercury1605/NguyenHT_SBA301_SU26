module hsf302.javafx.com {
    requires javafx.controls;
    requires javafx.fxml;
    requires sba301.com; // Gọi module từ project Hibernate sang
    requires jbcrypt;

    opens sba301.fe.controller to javafx.fxml;
    exports sba301.fe.controller;
}