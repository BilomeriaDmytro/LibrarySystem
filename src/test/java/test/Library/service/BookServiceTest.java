package test.Library.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import test.Library.dto.BookDTO;
import test.Library.model.Book;
import test.Library.model.EntityStatus;
import test.Library.model.User;
import test.Library.repository.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void addBookTest(){

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("New book");

        Book book = bookService.addBook(bookDTO);
        Assert.assertNotNull(book);
        Assert.assertEquals(book.getName(), bookDTO.getName());
        Assert.assertEquals(book.getStatus(), EntityStatus.ACTIVE);
    }

    @Test
    public void deleteBookTest(){

        Book book = new Book();

        bookService.deleteBook(book);

        Assert.assertEquals(book.getStatus(), EntityStatus.DELETED);
    }

    @Test
    public void updateBookTest(){

        Book oldBook = new Book();
        oldBook.setName("Old book");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("New book");

        Book book = bookService.updateBook(oldBook, bookDTO);

        Assert.assertEquals(book.getName(), bookDTO.getName());
    }

    @Test
    public void takeBookAndReturn(){

        Book book = new Book();
        book.setFree(true);
        User user = new User();

        boolean taken = bookService.takeBook(user,book);
        Assert.assertTrue(taken);
        Assert.assertEquals(book.getUser(),user);

        boolean returned = bookService.returnBook(user, book);

        Assert.assertTrue(returned);
        Assert.assertNull(book.getUser());

    }

}
