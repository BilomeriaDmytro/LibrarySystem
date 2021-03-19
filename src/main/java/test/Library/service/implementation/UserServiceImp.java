package test.Library.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.Library.dto.UserDTO;
import test.Library.model.Book;
import test.Library.model.EntityStatus;
import test.Library.model.User;
import test.Library.repository.UserRepository;
import test.Library.service.BookService;
import test.Library.service.UserService;
import test.Library.service.exceptionHandler.exception.BadRequestParameters;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    @Override
    public User getById(Long id) {

        log.info("Trying to get user with id - {}...", id);
        User user = userRepository.findById(id).orElse(null);
        if(user == null || user.getStatus() != EntityStatus.ACTIVE){
            log.info("User with id - {} not found.", id);
            return null;
        }
        log.info("User with id - {} successfully found.", id);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserDTO userDTO) {

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setStatus(EntityStatus.ACTIVE);
        user.setBooks(new ArrayList<Book>());
        userRepository.save(user);
        log.info("New user successfully created.");
        return user;
    }

    @Override
    public User updateUser(User user, UserDTO userDTO) {

        Long id = user.getId();

        log.info("User with id - {}  updating [{},{}]...", id, user.getFirstName(), user.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        log.info("User with id - {} is updated [{},{}].", id, user.getFirstName(), user.getLastName());

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(User user) {

        user.setStatus(EntityStatus.DELETED);
        userRepository.save(user);
        log.info("User with id - {} successfully deleted.", user.getId());
    }


}
