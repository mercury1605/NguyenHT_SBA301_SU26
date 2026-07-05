package sba301.fu.repository;

import sba301.fu.dao.StudentDAO;
import sba301.fu.pojo.Student;

import java.util.List;

public class StudentRepository implements IStudentRepository {

    private StudentDAO dao = new StudentDAO();
    @Override
    public List<Student> findAll() {
        return dao.getStudents();
    }

    @Override
    public void save(Student student) {
         dao.save(student);
    }

    @Override
    public void delete(int studentID) {
        dao.delete(studentID);
    }

    @Override
    public Student findById(int studentID) {
        return dao.findById(studentID);
    }

    @Override
    public void update(Student student) {
        dao.update(student);
    }
}
