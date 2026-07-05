package com.fucar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Thay đổi đường dẫn chính xác tuyệt đối từ root resources
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/fucar/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 500, 400);
        stage.setTitle("FUCarRentingSystem v1.0");
        stage.setResizable(false);
        stage.setScene(scene);

        // Đăng ký cơ chế bắt phím tắt toàn cục trên Stage
        scene.setOnKeyPressed(event -> {
            // Kiểm tra xem màn hình hiện tại có phải là MainView không bằng cách check tiêu đề Stage
            if (stage.getTitle().contains("Hệ thống quản lý thuê xe")) {
                try {
                    // Lấy ra Controller điều hướng của màn hình tổng thể
                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/fucar/main-view.fxml"));
                    // Mẹo tìm nạp nhanh đối tượng điều phối sự kiện phím tắt
                    if (scene.getRoot() instanceof javafx.scene.layout.BorderPane) {
                        // Nếu cần tùy biến sâu phím tắt điều hướng có thể ép kiểu xử lý trực tiếp tại đây
                    }
                } catch (Exception ex) {
                    // Bỏ qua nếu lỗi nạp
                }
            }

            // Phím tắt ESCAPE toàn cục: Tự động đóng bất kỳ cửa sổ Dialog nhỏ nào đang mở đè lên
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                javafx.stage.Stage.getWindows().stream()
                        .filter(w -> w.isShowing() && w instanceof javafx.stage.Stage && w != stage)
                        .findFirst()
                        .ifPresent(window -> ((javafx.stage.Stage) window).close());
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}