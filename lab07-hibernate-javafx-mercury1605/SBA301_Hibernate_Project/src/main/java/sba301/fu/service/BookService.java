package sba301.fu.service;

import sba301.fu.pojo.Book;
import sba301.fu.repository.BookRepository;
import sba301.fu.repository.IBookRepository;

import java.util.List;

public class BookService implements IBookService {
    private final IBookRepository repo = new BookRepository();

    @Override
    public void save(Book b) {
        repo.save(b);
    }

    @Override
    public List<Book> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }

    @Override
    public Book findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void update(Book b) {
        repo.update(b);
    }

    @Override
    public List<Book> findBooksByStudentId(int sid) {
        return repo.findBooksByStudentId(sid);
    }
}
