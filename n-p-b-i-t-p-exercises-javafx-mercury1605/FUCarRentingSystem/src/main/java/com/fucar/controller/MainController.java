package com.fucar.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML
    private StackPane contentArea;
    @FXML
    private Label lblStatus, lblDateTime;
    @FXML
    private Button btnCars, btnCustomers, btnRental, btnReport;

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML
    public void initialize() {
        startClock();
        setupToolbarTooltips();
    }

    // Cơ chế chạy đồng hồ cập nhật mỗi giây một lần bằng Timeline
    private void startClock() {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> lblDateTime.setText(LocalDateTime.now().format(DT_FMT)))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
        lblDateTime.setText(LocalDateTime.now().format(DT_FMT));
    }

    // Thiết lập Tooltip hiển thị sau 300ms khi hover chuột vào nút công cụ
    private void setupToolbarTooltips() {
        addTooltip(btnCars, "Quản lý xe (Ctrl+1)");
        addTooltip(btnCustomers, "Quản lý khách hàng (Ctrl+2)");
        addTooltip(btnRental, "Giao dịch thuê xe (Ctrl+3)");
        addTooltip(btnReport, "Báo cáo thống kê (Ctrl+4)");
    }

    private void addTooltip(Button btn, String text) {
        Tooltip tip = new Tooltip(text);
        tip.setShowDelay(Duration.millis(300));
        btn.setTooltip(tip);
    }

    // ── Các hàm hoán đổi View động vào tâm màn hình chính ──
    @FXML
    public void navCars() {
        loadSubView("/com/fucar/car-dialog.fxml", "Màn hình Quản lý Xe");
    }

    @FXML
    public void navCustomers() {
        loadSubView("/com/fucar/customer-view.fxml", "Màn hình Quản lý Khách hàng");
    }

    @FXML
    public void navRental() {
        loadSubView("/com/fucar/rental-view.fxml", "Màn hình Tạo Giao dịch Thuê Xe");
    }

    @FXML
    public void navReport() {
        loadSubView("/com/fucar/report-view.fxml", "Màn hình Báo cáo Thống kê");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private void loadSubView(String fxmlPath, String viewTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent node = loader.load();
            contentArea.getChildren().setAll(node);
            lblStatus.setText("Đang hiển thị: " + viewTitle);
        } catch (IOException e) {
            lblStatus.setText("Lỗi nạp màn hình: " + viewTitle);
            e.printStackTrace();
        }
    }
}