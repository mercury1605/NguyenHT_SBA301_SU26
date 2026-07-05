// 2. BookRepository.java (gói sba301.fu.repository)
package sba301.fu.repository;
import sba301.fu.dao.BookDAO;
import sba301.fu.pojo.Book;
import java.util.List;
public class BookRepository implements IBookRepository {
    private final BookDAO bookDAO = new BookDAO();
    @Override public void save(Book b) { bookDAO.save(b); }
    @Override public List<Book> findAll() { return bookDAO.getBooks(); }
    @Override public void delete(Long id) { bookDAO.delete(id); }
    @Override public Book findById(Long id) { return bookDAO.findById(id); }
    @Override public void update(Book b) { bookDAO.update(b); }
    @Override public List<Book> findBooksByStudentId(int sid) { return bookDAO.findBooksByStudentId(sid); }
}