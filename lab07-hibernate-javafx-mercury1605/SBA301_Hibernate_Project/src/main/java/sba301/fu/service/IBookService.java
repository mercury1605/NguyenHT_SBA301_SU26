package sba301.fu.service;

import sba301.fu.pojo.Book;

import java.util.List;

public interface IBookService {
    List<Book> findAll();
    void save(Book book);
    void delete(Long bookId);
    Book findById(Long bookId);
    void update(Book book);
    List<Book> findBooksByStudentId(int studentId);
}
