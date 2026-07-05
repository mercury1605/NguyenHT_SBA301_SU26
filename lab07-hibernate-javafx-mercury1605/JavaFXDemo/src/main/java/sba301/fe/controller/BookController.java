package sba301.fe.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sba301.fu.pojo.Book;
import sba301.fu.pojo.Student;
import sba301.fu.service.BookService;
import sba301.fu.service.IBookService;

public class BookController {

    @FXML private TableView<Book> tbBook;
    @FXML private TableColumn<Book, Long> colBookId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colIsbn;

    @FXML private TextField txtTitle;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtIsbn;

    private final IBookService bookService = new BookService();
    private ObservableList<Book> bookModels;
    private Student selectedStudent;
    private Long currentBookId = 0L;

    // Hàm nhận dữ liệu Student từ màn hình chính truyền sang
    public void setStudent(Student student) {
        this.selectedStudent = student;
        loadBookData();
    }

    private void loadBookData() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        bookModels = FXCollections.observableArrayList(bookService.findBooksByStudentId(selectedStudent.getId()));
        tbBook.setItems(bookModels);

        tbBook.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentBookId = newVal.getId();
                txtTitle.setText(newVal.getTitle());
                txtAuthor.setText(newVal.getAuthor());
                txtIsbn.setText(newVal.getIsbn());
            }
        });
    }

    @FXML
    public void addBook() {
        if (txtTitle.getText().isBlank() || txtAuthor.getText().isBlank() || txtIsbn.getText().isBlank()) {
            return;
        }
        Book book = new Book(txtTitle.getText(), txtAuthor.getText(), txtIsbn.getText());
        book.setStudent(selectedStudent); // Khóa ngoại gán trực tiếp cho sinh viên hiện hành

        bookService.save(book);
        clearFields();
        loadBookData();
    }

    @FXML
    public void deleteBook() {
        if (currentBookId == 0L) return;
        bookService.delete(currentBookId);
        clearFields();
        loadBookData();
    }

    private void clearFields() {
        currentBookId = 0L;
        txtTitle.clear();
        txtAuthor.clear();
        txtIsbn.clear();
    }
}