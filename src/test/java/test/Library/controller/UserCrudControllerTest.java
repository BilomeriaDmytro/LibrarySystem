package test.Library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.Library.dto.UserDTO;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = ("/application-test.properties"))
@Sql(value = {"/sql/create-instances-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete-instances-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserCrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserSuccess() throws Exception {
        this.mockMvc.perform(get("/user?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Ivan\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Sidorov\"")));
    }

    @Test
    void getUserFailure() throws Exception {
        this.mockMvc.perform(get("/user?id=99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with id - 99, was not found")));
    }

    @Test
    void addUserSuccess() throws Exception {

        UserDTO userDTO= new UserDTO();

        userDTO.setFirstName("Name");
        userDTO.setLastName("Surname");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/user")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\":\"ACTIVE\"")));
    }

    @Test
    void addUserFailure() throws Exception {

        UserDTO userDTO= new UserDTO();

        userDTO.setFirstName("dsasa");
        userDTO.setLastName("Surname");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/user")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Errors appears at fields then validating form.")));
    }

    @Test
    void updateUserSuccess() throws Exception {

        UserDTO userDTO= new UserDTO();

        userDTO.setFirstName("Name");
        userDTO.setLastName("Surname");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/user?id=1")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Name\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Surname\"")));
    }

    @Test
    public void updateUserFailure() throws Exception {

        UserDTO userDTO= new UserDTO();

        userDTO.setFirstName("ffas");
        userDTO.setLastName("Surname");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/user?id=1")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Errors appears at fields then validating form.")));
    }

    @Test
    void deleteUserSuccess() throws Exception {
        this.mockMvc.perform(delete("/user?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserFailure() throws Exception {
        this.mockMvc.perform(delete("/user?id=99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with id - 99, was not found")));
    }
}