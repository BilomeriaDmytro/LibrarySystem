package test.Library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import test.Library.dto.BookDTO;
import test.Library.model.Book;
import test.Library.service.BookService;
import test.Library.service.exceptionHandler.exception.BadRequestParameters;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookCrudController {

    @Autowired
    private BookService bookService;

    @GetMapping(params = {"id"})
    public Book getBook(Long id) throws BadRequestParameters {

        Book book = bookService.getBookById(id);
        if(book == null){
            throw new BadRequestParameters("Book with id - " + id + ", was not found");
        }
        return book;
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody BookDTO bookDTO,
                        BindingResult result) throws BadRequestParameters {

        if(result.hasErrors()) {
            log.warn("Exception thrown then trying to add book - [{}], {} errors found",
                    bookDTO.getName(), result.getErrorCount());

            throw new BadRequestParameters("Errors appears at fields then validating form.", result.getFieldErrors());
        }

        Book book = bookService.addBook(bookDTO);
        return book;
    }

    @PostMapping(params = {"id"})
    public Book updateBook(@RequestParam("id") Long id,
                           @Valid @RequestBody BookDTO bookDTO,
                           BindingResult result) throws BadRequestParameters{

        if(result.hasErrors()) {
            log.warn("Exception thrown then trying to update book - [{}], {} errors found",
                    bookDTO.getName(), result.getErrorCount());

            throw new BadRequestParameters("Errors appears at fields then validating form.", result.getFieldErrors());
        }

        Book book = bookService.getBookById(id);
        if(book == null){
            throw new BadRequestParameters("Book with id - " + id + ", was not found");
        }
        bookService.updateBook(book, bookDTO);
        return book;
    }

    @DeleteMapping(params = {"id"})
    public void deleteBook(@RequestParam("id") Long id) throws BadRequestParameters {

        Book book = bookService.getBookById(id);

        if(book == null){
            throw new BadRequestParameters("Book with id - " + id + ", was not found");
        }

        bookService.deleteBook(book);
    }

}
