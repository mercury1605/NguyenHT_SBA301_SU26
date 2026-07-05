package sba301.fu.service;

import sba301.fu.pojo.Student;
import sba301.fu.repository.IStudentRepository;
import sba301.fu.repository.StudentRepository;

import java.util.List;

public class StudentService implements IStudentService {

    private IStudentRepository repo = new StudentRepository();

    public StudentService() {
    }

    // Bổ sung thêm Constructor này để phục vụ Unit Test (nhận Mock Repo truyền vào)
    public StudentService(IStudentRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Student> findAll() {
        return repo.findAll();
    }

    @Override
    public void save(Student student) {
        repo.save(student);

    }

    @Override
    public void delete(int studentID) {
        repo.delete(studentID);

    }

    @Override
    public Student findById(int studentID) {
        return repo.findById(studentID);
    }

    @Override
    public void update(Student student) {
        repo.update(student);
    }
}
