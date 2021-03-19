package test.Library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.Library.model.Book;
import test.Library.model.User;
import test.Library.service.BookService;
import test.Library.service.UserService;
import test.Library.service.exceptionHandler.exception.BadRequestParameters;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/library")
public class LibraryActionController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "userBooks", params = {"id"})
    public List<Book> getUserBooks(@RequestParam("id") Long id) throws BadRequestParameters {

        User user = userService.getById(id);

        if(user == null){
            throw new BadRequestParameters("User with id - " + id + " not found.");
        }

        List<Book> books = bookService.getUserBooks(user);
        log.info("Returning user books - {}", books.size());
        return books;
    }

    @PostMapping(value = "take", params = {"user_id", "book_id"})
    public Book takeBook(@RequestParam("user_id") Long user_id,
                         @RequestParam("book_id") Long book_id)
            throws BadRequestParameters {

        User user = userService.getById(user_id);

        if(user == null){
            throw new BadRequestParameters("User with id - " + user_id + " not found.");
        }

        Book book = bookService.getBookById(book_id);

        if(book == null){
            throw new BadRequestParameters("Book with id - " + book_id + " not found.");
        }

        boolean taken = bookService.takeBook(user, book);

        if(!taken){
            throw new BadRequestParameters("Book with id - " + book_id + " already taken.");
        }

        return bookService.getBookById(book_id);
    }

    @PostMapping(value = "return", params = {"user_id", "book_id"})
    public Book returnBook(@RequestParam("user_id") Long user_id,
                         @RequestParam("book_id") Long book_id)
            throws BadRequestParameters {

        User user = userService.getById(user_id);

        if(user == null){
            throw new BadRequestParameters("User with id - " + user_id + " not found.");
        }

        Book book = bookService.getBookById(book_id);

        if(book == null){
            throw new BadRequestParameters("Book with id - " + book_id + " not found.");
        }

        boolean returned = bookService.returnBook(user, book);

        if(!returned){
            throw new BadRequestParameters("Book with id - " + book_id + " was not taken by user with id " + user_id + ".");
        }

        return bookService.getBookById(book_id);
    }

    @GetMapping(value = "allBooks")
    public List<Book> getAllBooks() {

        List<Book> books = bookService.getAllBooks();
        log.info("Returning all books - {}", books.size());
        return books;
    }
}
