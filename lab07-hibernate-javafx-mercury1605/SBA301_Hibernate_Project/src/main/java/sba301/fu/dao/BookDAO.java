package sba301.fu.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba301.fu.pojo.Book;
import java.util.List;

public class BookDAO {

    public void save(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.save(book);
            t.commit();
        }
    }

    public List<Book> getBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            return query.list();
        }
    }

    public void delete(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                session.delete(book);
            }
            tx.commit();
        }
    }

    public Book findById(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, bookId);
        }
    }

    public void update(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.update(book);
            t.commit();
        }
    }

    // Hàm HQL nâng cao dùng để lọc danh sách sách thuộc sở hữu của 1 sinh viên
    public List<Book> findBooksByStudentId(int studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book where student.id = :sid", Book.class);
            query.setParameter("sid", studentId);
            return query.list();
        }
    }
}