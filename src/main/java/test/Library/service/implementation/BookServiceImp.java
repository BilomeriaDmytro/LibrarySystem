package test.Library.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.Library.dto.BookDTO;
import test.Library.model.Book;
import test.Library.model.EntityStatus;
import test.Library.model.User;
import test.Library.repository.BookRepository;
import test.Library.service.BookService;

import java.util.List;

@Service
@Slf4j
public class BookServiceImp implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getUserBooks(User user){

        List<Book> books = bookRepository.getByUserAndStatus(user,EntityStatus.ACTIVE);
        log.info("Returning books [{}] for user with id - {}", books.size(), user.getId());
        return books;
    }

    @Override
    public List<Book> getAllBooks() {

        List<Book> books = bookRepository.findByStatus(EntityStatus.ACTIVE);
        log.info("Returning all books [{}]", books.size());

        return books;
    }


    @Override
    public Book getBookById(Long id) {

        log.info("Trying to get book with id - {}...", id);
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null || book.getStatus() != EntityStatus.ACTIVE){
            log.info("Book with id - {} not found.", id);
            return null;
        }
        log.info("Book with id - {} successfully found.", id);
        return book;
    }

    @Override
    public Book updateBook(Book book, BookDTO bookDTO) {

    log.info("Book with id - {}  updating...", book.getId());
    book.setName(bookDTO.getName());
    bookRepository.save(book);
    log.info("Book with id - {} is updated [{}].", book.getId(), book.getName());
    return book;
    }

    @Override
    public void deleteBook(Book book) {

        book.setStatus(EntityStatus.DELETED);
        bookRepository.save(book);
        log.info("Book with id - {} successfully deleted.", book.getId());
    }

    @Override
    public Book addBook(BookDTO bookDTO) {

        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setStatus(EntityStatus.ACTIVE);
        book.setFree(true);
        bookRepository.save(book);
        log.info("New book with name - {} added, status - {}",book.getName(), book.getStatus());
        return book;
    }

    @Override
    public boolean takeBook(User user, Book book){

        if(!book.isFree()){
            log.info("Book with id -  {} already taken.", book.getId());
            return false;
        }
        book.setUser(user);
        book.setFree(false);
        bookRepository.save(book);
        log.info("Book with id -  {} taken by user with id - {}.", book.getId(), user.getId());
        return true;
    }

    @Override
    public boolean returnBook(User user, Book book ){

        User owner = book.getUser();

        if(owner != null && owner.equals(user)){
            book.setUser(null);
            book.setFree(true);
            bookRepository.save(book);
            log.info("Book with id - {} returned by user with id - {}", book.getId(), user.getId());
            return true;
        }
        return false;
    }
}
