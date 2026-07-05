package sba301.fe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sba301.fu.pojo.Student;
import sba301.fu.service.IStudentService;
import sba301.fu.service.StudentService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoginController {

    @FXML private TextField txtLoginEmail;
    @FXML private PasswordField txtLoginPassword;

    private final IStudentService iStudentService = new StudentService();

    @FXML
    public void login(ActionEvent event) {
        String email = txtLoginEmail.getText();
        String password = txtLoginPassword.getText();

        if ("admin@library.com".equals(email) && "admin123".equals(password)) {
            Student adminSession = new Student();
            adminSession.setId(-1);
            adminSession.setFirstName("Hệ thống");
            adminSession.setLastName("Admin");
            adminSession.setEmail(email);

            SessionContext.setLoggedInStudent(adminSession);
            openStudentManagementScreen(event);
            return;
        }

        List<Student> students = iStudentService.findAll();
        Student matched = students.stream()
                .filter(s -> s.getEmail().equals(email))
                .filter(s -> BCrypt.checkpw(password, s.getPassword()))
                .findFirst()
                .orElse(null);

        if (matched != null) {
            SessionContext.setLoggedInStudent(matched);
            openStudentManagementScreen(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login Failed");
            alert.setContentText("Email hoặc mật khẩu không đúng!");
            alert.showAndWait();
        }
    }

    private void openStudentManagementScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Library - Manage Student");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}