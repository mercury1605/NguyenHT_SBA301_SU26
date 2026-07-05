package sba301.fu.pojo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String password;

    @Column(name = "fistName") // Giữ nguyên theo tài liệu mẫu
    private String firstName;
    private String lastName;
    private int marks;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "student")
    private Set<Book> books;

    public Student() {}
    public Student(String email, String password, String firstName, String lastName, int marks) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.marks = marks;
    }
    public Student(int id, String email, String password, String firstName, String lastName, int marks) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.marks = marks;
    }
    // Getter & Setter cho tất cả các thuộc tính...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }


}
