package com.fucar.controller;

import com.fucar.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListController {
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, Double> colScore;
    @FXML
    private TableColumn<Student, String> colGrade;

    // Khai báo thêm nút Xem chi tiết từ FXML
    @FXML
    private Button btnDetail;

    @FXML
    public void initialize() {
        // 1. Map các thuộc tính của Student vào cột của TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // 2. Đổ dữ liệu mẫu
        tableView.setItems(FXCollections.observableArrayList(
                new Student(1, "Nguyễn Văn An", 8.5),
                new Student(2, "Trần Thị Bình", 7.0),
                new Student(3, "Lê Văn Cường", 5.5),
                new Student(4, "Phạm Thị Dung", 4.0),
                new Student(5, "Hoàng Văn Em", 9.0)
        ));

        // 3. Property Binding: Tự động BẬT nút khi có dòng được chọn, TẮT nút khi bảng trống
        btnDetail.disableProperty().bind(
                tableView.getSelectionModel().selectedItemProperty().isNull()
        );

        btnDetail.setOnAction(e -> handleOpenCarDialog());
    }

    @FXML
    private void handleClose() {
        tableView.getScene().getWindow().hide();
    }

    private void handleOpenCarDialog() {
        try {
            // Khởi tạo FXMLLoader tải file car-dialog.fxml
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/fucar/car-dialog.fxml")
            );
            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setScene(new javafx.scene.Scene(loader.load()));
            dialogStage.setResizable(false);

            // Khóa màn hình cha lại (Modality.APPLICATION_MODAL) bắt buộc xử lý xong dialog mới quay lại được
            dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialogStage.initOwner(tableView.getScene().getWindow());
            dialogStage.setTitle("Quản Lý Xe - Thêm Xe Mới");

            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}