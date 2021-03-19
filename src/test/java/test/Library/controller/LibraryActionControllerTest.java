package test.Library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.Library.model.Book;
import test.Library.model.User;
import test.Library.service.BookService;
import test.Library.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = ("/application-test.properties"))
@Sql(value = {"/sql/create-instances-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete-instances-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LibraryActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Test
    public void getUserBooksSuccess() throws Exception {

        this.mockMvc.perform(get("/library/userBooks?id=3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Crime and punishment")))
                .andExpect(content().string(containsString("Norwegian wood")))
                .andExpect(content().string(containsString("Brief history of time")));
    }

    @Test
    public void getUserBooksFailure() throws Exception {

        this.mockMvc.perform(get("/library/userBooks?id=99"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("User with id - 99 not found")));
    }

    @Test
    public void takeBookSuccess() throws Exception {

        this.mockMvc.perform(post("/library/take?user_id=2&book_id=4"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"free\":false}")));
    }

    @Test
    public void takeBookFailure() throws Exception {

        this.mockMvc.perform(post("/library/take?user_id=2&book_id=1"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Book with id - 1 already taken")));
    }

    @Test
    public void returnBookSuccess() throws Exception {

        this.mockMvc.perform(post("/library/return?user_id=3&book_id=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"free\":true}")));
    }

    @Test
    public void returnBookFailure() throws Exception {

        this.mockMvc.perform(post("/library/return?user_id=1&book_id=7"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Book with id - 7 was not taken by user with id 1.")));
    }
}
