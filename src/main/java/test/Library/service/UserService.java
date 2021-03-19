package test.Library.service;

import test.Library.dto.UserDTO;
import test.Library.model.Book;
import test.Library.model.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    List<User> getAllUsers();

    User addUser(UserDTO userDTO);

    User updateUser(User user, UserDTO userDTO);

    void deleteUser(User user);


}
