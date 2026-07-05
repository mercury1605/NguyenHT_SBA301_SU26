package com.fucar.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField accountNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordVisible;
    @FXML
    private CheckBox showPasswordCheck;
    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        lblError.setText("");
        passwordField.textProperty().bindBidirectional(passwordVisible.textProperty());
    }

    @FXML
    private void handleShowPassword() {
        boolean show = showPasswordCheck.isSelected();
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);
        passwordVisible.setVisible(show);
        passwordVisible.setManaged(show);
    }

    @FXML
    private void handleLogin() {
        String username = accountNameField.getText().trim();
        String password = showPasswordCheck.isSelected() ? passwordVisible.getText() : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("⚠ Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // Kiểm tra tài khoản (Có thể dùng admin/admin123)
        if (username.equals("admin") && password.equals("admin123")) {
            navigateToListView();
        } else {
            lblError.setText("⚠ Sai tên tài khoản hoặc mật khẩu.");
        }
    }

    // Hàm thực hiện chuyển màn hình từ Login sang List View
    private void navigateToListView() {
        try {
            // Sửa tạm dòng này trong LoginController.java để test bài NC4
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fucar/main-view.fxml"));
            Scene mainScene = new Scene(loader.load(), 1024, 680);

            // 2. Lấy Stage hiện tại từ bất kỳ control nào (ở đây lấy từ accountNameField)
            Stage currentStage = (Stage) accountNameField.getScene().getWindow();

            // 3. Thay đổi scene trên Stage cũ sang giao diện mới
            currentStage.setScene(mainScene);
            currentStage.setTitle("FUCarRentingSystem v1.0 - Hệ thống quản lý thuê xe");
            currentStage.setResizable(true); // Cho phép thu phóng màn hình mới
            currentStage.centerOnScreen();   // Đưa màn hình ra giữa cho đẹp

        } catch (IOException e) {
            lblError.setText("⚠ Lỗi nạp màn hình chính: " + e.getMessage());
            e.printStackTrace();
        }
    }
}