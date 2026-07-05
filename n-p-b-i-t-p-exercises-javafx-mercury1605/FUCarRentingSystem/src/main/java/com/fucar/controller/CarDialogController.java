package com.fucar.controller;

import com.fucar.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CarDialogController {

    @FXML
    private Label lblTitle;
    @FXML
    private TextField tfCarId, tfCarName, tfRentPrice;
    @FXML
    private Spinner<Integer> spinnerYear, spinnerCapacity;
    @FXML
    private ComboBox<String> cbColor, cbProducer;
    @FXML
    private DatePicker dpImport;
    @FXML
    private TextArea taDesc;
    @FXML
    private RadioButton rbAvailable, rbRenting, rbMaintenance;
    @FXML
    private Label errCarName, errRentPrice, errImport, errProducer;

    private ToggleGroup statusGroup;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        // Spinner năm SX: giới hạn 1990–2030, mặc định lấy năm hiện tại
        spinnerYear.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2030, LocalDate.now().getYear()));

        // Spinner số chỗ ngồi: giới hạn từ 2–16 chỗ, mặc định là 4
        spinnerCapacity.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 16, 4));

        // Nạp danh sách màu sắc mẫu
        cbColor.getItems().addAll("Trắng", "Đen", "Bạc", "Đỏ", "Xanh", "Vàng", "Khác");
        cbColor.setValue("Trắng");

        // Nạp danh sách hãng sản xuất
        cbProducer.getItems().addAll("Toyota", "Honda", "Ford", "VinFast", "Mazda");

        // Nhóm các RadioButton trạng thái lại để chỉ chọn được một mục
        statusGroup = new ToggleGroup();
        rbAvailable.setToggleGroup(statusGroup);
        rbRenting.setToggleGroup(statusGroup);
        rbMaintenance.setToggleGroup(statusGroup);
        rbAvailable.setSelected(true);

        // Regex Listener: Chỉ cho phép nhập số vào ô giá thuê
        tfRentPrice.textProperty().addListener((obs, old, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*")) tfRentPrice.setText(old);
        });

        // Xóa thông báo lỗi inline một cách mượt mà ngay khi người dùng gõ/chọn lại dữ liệu mới
        tfCarName.textProperty().addListener((o, ov, nv) -> {
            errCarName.setText("");
            tfCarName.getStyleClass().remove("field-error");
        });
        tfRentPrice.textProperty().addListener((o, ov, nv) -> {
            errRentPrice.setText("");
            tfRentPrice.getStyleClass().remove("field-error");
        });
        dpImport.valueProperty().addListener((o, ov, nv) -> errImport.setText(""));
        cbProducer.valueProperty().addListener((o, ov, nv) -> errProducer.setText(""));
    }

    public void setEditMode(Car car) {
        isEditMode = true;
        lblTitle.setText("Chỉnh sửa thông tin xe");
        tfCarId.setText(car.getCarId());
        tfCarName.setText(car.getCarName());
        spinnerYear.getValueFactory().setValue(car.getModelYear());
        cbColor.setValue(car.getColor());
        spinnerCapacity.getValueFactory().setValue(car.getCapacity());
        tfRentPrice.setText(String.valueOf(car.getRentPrice()));
        dpImport.setValue(car.getImportDate());
        cbProducer.setValue(car.getProducer());
        taDesc.setText(car.getDescription());
        switch (car.getStatus()) {
            case "Đang cho thuê" -> rbRenting.setSelected(true);
            case "Bảo dưỡng" -> rbMaintenance.setSelected(true);
            default -> rbAvailable.setSelected(true);
        }
    }

    @FXML
    private void handleSave() {
        boolean valid = true;

        // Kiểm tra Tên xe từ 3 ký tự trở lên
        if (tfCarName.getText().trim().length() < 3) {
            errCarName.setText("Tên xe phải có từ 3–100 ký tự.");
            if (!tfCarName.getStyleClass().contains("field-error")) {
                tfCarName.getStyleClass().add("field-error");
            }
            valid = false;
        }

        // Kiểm tra Giá thuê phải lớn hơn 0
        try {
            double price = Double.parseDouble(tfRentPrice.getText().trim());
            if (price <= 0) {
                errRentPrice.setText("Giá thuê phải lớn hơn 0 VNĐ.");
                if (!tfRentPrice.getStyleClass().contains("field-error"))
                    tfRentPrice.getStyleClass().add("field-error");
                valid = false;
            }
        } catch (NumberFormatException e) {
            errRentPrice.setText("Giá thuê không hợp lệ.");
            if (!tfRentPrice.getStyleClass().contains("field-error")) tfRentPrice.getStyleClass().add("field-error");
            valid = false;
        }

        // Kiểm tra Ngày nhập không trống và không thuộc về tương lai
        if (dpImport.getValue() == null) {
            errImport.setText("Vui lòng chọn ngày nhập xe.");
            valid = false;
        } else if (dpImport.getValue().isAfter(LocalDate.now())) {
            errImport.setText("Ngày nhập xe không được ở tương lai.");
            valid = false;
        }

        // Kiểm tra đã chọn hãng sản xuất chưa
        if (cbProducer.getValue() == null) {
            errProducer.setText("Vui lòng chọn nhà sản xuất.");
            valid = false;
        }

        if (!valid) return;

        // Bật hộp thoại xác nhận khi dữ liệu hoàn toàn hợp lệ
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Xác nhận " + (isEditMode ? "cập nhật" : "thêm mới") + " thông tin xe?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) closeDialog();
        });
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        ((Stage) tfCarName.getScene().getWindow()).close();
    }
}