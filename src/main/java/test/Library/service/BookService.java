package test.Library.service;

import test.Library.dto.BookDTO;
import test.Library.model.Book;
import test.Library.model.User;

import java.util.List;

public interface BookService {

    List<Book> getUserBooks(User user);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book updateBook(Book book, BookDTO bookDTO);

    void deleteBook(Book book);

    Book addBook(BookDTO bookDTO);

    boolean takeBook(User user, Book book);

    boolean returnBook(User user, Book book);
}
