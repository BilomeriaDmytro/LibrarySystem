package test.Library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import test.Library.dto.BookDTO;
import test.Library.service.BookService;
import test.Library.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = ("/application-test.properties"))
@Sql(value = {"/sql/create-instances-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete-instances-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookCrudControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBookSuccess() throws Exception {

        this.mockMvc.perform(get("/book?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"name\":\"Crime and punishment\"")));
    }

    @Test
    public void getBookFailure() throws Exception {

        this.mockMvc.perform(get("/book?id=99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Book with id - 99, was not found")));
    }

    @Test
    public void addBookSuccess() throws Exception {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("New book name");


        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookDTO);

        this.mockMvc.perform(post("/book")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    public void addBookFailure() throws Exception {

        BookDTO bookDTO = new BookDTO();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookDTO);

        this.mockMvc.perform(post("/book")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Errors appears at fields then validating form.")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("fieldErrors")));
    }

    @Test
    public void updateBookSuccess() throws Exception {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("New book name");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookDTO);

        this.mockMvc.perform(post("/book?id=1")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("New book name")));
    }

    @Test
    public void updateBookFailure() throws Exception {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Afds?fsa dsa");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookDTO);

        this.mockMvc.perform(post("/book?id=1")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Wrong name format")));
    }

    @Test
    public void deleteBookSuccess() throws Exception {

        this.mockMvc.perform(delete("/book?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookFailure() throws Exception {

        this.mockMvc.perform(delete("/book?id=99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book with id - 99, was not found")));
    }

}
