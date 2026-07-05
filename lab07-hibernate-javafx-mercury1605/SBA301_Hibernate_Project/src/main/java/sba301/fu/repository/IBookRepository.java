// 1. IBookRepository.java (gói sba301.fu.repository)
package sba301.fu.repository;
import sba301.fu.pojo.Book;
import java.util.List;
public interface IBookRepository {
    List<Book> findAll();
    void save(Book book);
    void delete(Long bookId);
    Book findById(Long bookId);
    void update(Book book);
    List<Book> findBooksByStudentId(int studentId);
}