package sba301.fu.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sba301.fu.pojo.Student;

import java.util.List;

public class StudentDAO {


    public StudentDAO() {
    }

    public void save(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.save(student);
            t.commit();
        }
    }

    public List<Student> getStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Student", Student.class);
            return query.list();
        } catch (Exception ex) {
            return null;
        }
    }

    public void delete(int studentID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Student student = session.get(Student.class, studentID);
            if (student != null) {
                session.delete(student);
            }
            tx.commit();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Student findById(int studentID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, studentID);
        }
    }

    public void update(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.update(student);
            t.commit();
        } catch (Exception ex) {
            throw ex;
        }
    }
}