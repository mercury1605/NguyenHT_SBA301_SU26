package sba301.fe.controller;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sba301.fu.pojo.Student;
import sba301.fu.service.IStudentService;
import sba301.fu.service.StudentService;
import org.mindrot.jbcrypt.BCrypt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentController implements Initializable {

    @FXML
    private TableView<Student> tbData;
    @FXML
    public TableColumn<Student, Integer> studentId;
    @FXML
    public TableColumn<Student, String> email;
    @FXML
    public TableColumn<Student, String> password;
    @FXML
    public TableColumn<Student, String> firstName;
    @FXML
    public TableColumn<Student, String> lastName;
    @FXML
    public TableColumn<Student, Integer> totalMark;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtTotalMark;
    @FXML
    private TextField txtSearch;
    @FXML private Label lblWelcome;

    @FXML private Button btnPrev;
    @FXML private Button btnNext;
    @FXML private Label lblPageInfo;

    private static final String PASSWORD_MASK = "••••••";

    private int idStudent;
    private IStudentService iStudentService;
    private ObservableList<Student> studentsModels;

    private int currentPage = 1;
    private final int rowsPerPage = 5; // Cấu hình hiển thị 5 dòng mỗi trang
    private FilteredList<Student> filteredData;

    public StudentController() {
        iStudentService = new StudentService();
        studentsModels = FXCollections.observableArrayList(iStudentService.findAll());
    }

    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showStudent(Student student) {
        this.setIdStudent(student.getId());
        this.txtFirstName.setText(student.getFirstName());
        this.txtEmail.setText(student.getEmail());
        this.txtPassword.setText(PASSWORD_MASK);
        this.txtLastName.setText(student.getLastName());
        this.txtTotalMark.setText(String.valueOf(student.getMarks()));
    }

    private void refreshDataTable() {
        this.setIdStudent(0);
        this.txtFirstName.setText("");
        this.txtLastName.setText("");
        this.txtTotalMark.setText("");
        this.txtEmail.setText("");
        this.txtPassword.setText("");
        txtSearch.setText("");
        studentsModels = FXCollections.observableArrayList(iStudentService.findAll());
        filteredData = new FilteredList<>(studentsModels, s -> true);
        currentPage = 1;
        updatePagination();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        totalMark.setCellValueFactory(new PropertyValueFactory<>("marks"));

        // 1. Khởi tạo FilteredList từ danh sách gốc
        filteredData = new FilteredList<>(studentsModels, s -> true);

        // 2. Lắng nghe ô tìm kiếm real-time
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            String keyword = newVal == null ? "" : newVal.toLowerCase().trim();
            filteredData.setPredicate(student -> {
                if (keyword.isEmpty()) return true;
                return student.getFirstName().toLowerCase().contains(keyword)
                        || student.getLastName().toLowerCase().contains(keyword)
                        || student.getEmail().toLowerCase().contains(keyword);
            });
            currentPage = 1; // Reset về trang 1 khi gõ tìm kiếm
            updatePagination();
        });

        // 3. Gọi hàm cập nhật phân trang ban đầu
        updatePagination();

        // 4. Lắng nghe bộ chọn dòng trên TableView
        tbData.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                showStudent(newValue);
            }
        });
        // Hiển thị tên người dùng lấy từ Session khi màn hình được nạp lên
        if (SessionContext.getLoggedInStudent() != null) {
            Student current = SessionContext.getLoggedInStudent();
            lblWelcome.setText("Xin chào, " + current.getFirstName() + " " + current.getLastName());
        }
    }

    @FXML
    public void openBookManagement() {
        if (this.getIdStudent() == 0) {
            showAlert("Thông báo", "Vui lòng chọn 1 sinh viên trong danh sách để quản lý sách!");
            return;
        }

        try {
            // Lấy thông tin thực thể Student hiện tại ra
            Student currentStudent = iStudentService.findById(this.getIdStudent());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("book-view.fxml"));
            javafx.scene.Parent root = loader.load();

            // Gửi đối tượng Student sang bộ điều khiển BookController
            BookController bookController = loader.getController();
            bookController.setStudent(currentStudent);

            // Khởi tạo một Pop-up Stage mới
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books Owned by: " + currentStudent.getFirstName());
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Chặn tương tác màn hình cũ khi đang mở pop-up
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudent() {
        if (!isValidInput()) return; // Validation chặn đầu

        String rawPassword = this.txtPassword.getText();
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        Student student = new Student(this.txtEmail.getText(), hashedPassword, this.txtFirstName.getText(), this.txtLastName.getText(),
                Integer.parseInt(txtTotalMark.getText()));
        iStudentService.save(student);
        refreshDataTable();
    }

    @FXML
    public void deleteStudent() {
        if (this.getIdStudent() == 0) {
            showAlert("Thông báo", "Vui lòng chọn 1 dòng sinh viên để xóa!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa sinh viên này không?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Xác nhận hành động xóa");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                iStudentService.delete(this.getIdStudent());
                refreshDataTable();
            }
        });
    }

    @FXML
    public void updateStudent() {
        if (this.getIdStudent() == 0) {
            showAlert("Thông báo", "Vui lòng chọn 1 dòng sinh viên trong bảng để cập nhật!");
            return;
        }
        if (!isValidInput()) return;

        // ponytail: ô password đang hiển thị mask "••••••" nghĩa là không đổi -> giữ hash cũ;
        // nếu người dùng gõ mật khẩu mới thì hash lại. Tránh ghi đè hash bằng chuỗi mask.
        String typed = this.txtPassword.getText();
        String finalPassword = PASSWORD_MASK.equals(typed)
                ? iStudentService.findById(this.idStudent).getPassword()
                : BCrypt.hashpw(typed, BCrypt.gensalt());

        Student student = new Student(this.idStudent, this.txtEmail.getText(), finalPassword,
                this.txtFirstName.getText(), this.txtLastName.getText(),
                Integer.parseInt(txtTotalMark.getText()));
        iStudentService.update(student);
        refreshDataTable();
    }


    @FXML
    public void logout(ActionEvent event) {
        SessionContext.clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginGUI.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Library - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePagination();
        }
    }

    @FXML
    public void goToNextPage() {
        int totalItems = filteredData.size();
        int pageCount = (int) Math.ceil((double) totalItems / rowsPerPage);
        if (currentPage < pageCount) {
            currentPage++;
            updatePagination();
        }
    }


    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }


    private boolean isValidInput() {
        if (txtEmail.getText().isBlank() || txtFirstName.getText().isBlank() || txtLastName.getText().isBlank()) {
            showAlert("Validation Error", "Email, First Name, Last Name không được để trống!");
            return false;
        }
        // Kiểm tra định dạng email bằng Regex
        if (!txtEmail.getText().matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            showAlert("Validation Error", "Email không đúng định dạng mẫu (VD: abc@fpt.edu.vn)!");
            return false;
        }
        try {
            int marks = Integer.parseInt(txtTotalMark.getText());
            if (marks < 0 || marks > 10) {
                showAlert("Validation Error", "Marks phải nằm trong khoảng từ 0 đến 10!");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Total Mark phải nhập vào là một số nguyên!");
            return false;
        }
        return true;
    }

    private void updatePagination() {
        int totalItems = filteredData.size();
        int pageCount = (int) Math.ceil((double) totalItems / rowsPerPage);
        if (pageCount == 0) pageCount = 1;

        if (currentPage > pageCount) currentPage = pageCount;

        // Cắt danh sách theo trang hiện tại
        int fromIndex = (currentPage - 1) * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, totalItems);

        ObservableList<Student> pageItems = FXCollections.observableArrayList();
        for (int i = fromIndex; i < toIndex; i++) {
            pageItems.add(filteredData.get(i));
        }

        // 5. Bọc danh sách phân trang vào SortedList để hỗ trợ Click-to-Sort cột
        SortedList<Student> sortedData = new SortedList<>(pageItems);
        sortedData.comparatorProperty().bind(tbData.comparatorProperty()); // Đồng bộ bộ so sánh với TableView

        tbData.setItems(sortedData);

        // Cập nhật trạng thái các nút và nhãn hiển thị
        lblPageInfo.setText("Page " + currentPage + " of " + pageCount);
        btnPrev.setDisable(currentPage == 1);
        btnNext.setDisable(currentPage == pageCount);
    }
}