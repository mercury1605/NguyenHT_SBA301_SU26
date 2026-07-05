package sba301.fu.repository;

import sba301.fu.pojo.Student;

import java.util.List;

public interface IStudentRepository {
    List<Student> findAll();
    void save(Student student);
    void delete(int studentID);
    Student findById(int studentID);
    void update(Student student);
}
