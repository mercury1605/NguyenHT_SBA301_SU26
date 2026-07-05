package sba301.fu.service;

import sba301.fu.pojo.Student;

import java.util.List;

public interface IStudentService {
    List<Student> findAll();
    void save(Student student);
    void delete(int studentID);
    Student findById(int studentID);
    void update(Student student);
}
