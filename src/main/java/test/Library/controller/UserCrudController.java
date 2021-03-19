package test.Library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import test.Library.dto.UserDTO;
import test.Library.model.User;
import test.Library.service.UserService;
import test.Library.service.exceptionHandler.exception.BadRequestParameters;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserCrudController {

    @Autowired
    private UserService userService;

    @GetMapping(params = {"id"})
    public User getUser(@RequestParam("id") Long id) throws BadRequestParameters {

        User user = userService.getById(id);

        if(user == null){
            throw new BadRequestParameters("User with id - " + id + ", was not found");
        }
        return user;
    }

    @GetMapping
    public List<User> getUsers() {

        List<User> users = userService.getAllUsers();

        return users;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) throws BadRequestParameters {

        if(result.hasErrors()) {
            log.warn("Exception thrown then trying to register user - [{}, {}], {} errors found",
                    userDTO.getFirstName(), userDTO.getLastName(), result.getErrorCount());

            throw new BadRequestParameters("Errors appears at fields then validating form.", result.getFieldErrors());
        }

        User user = userService.addUser(userDTO);

        return user;
    }

    @PostMapping(params = {"id"})
    public User updateUser(@RequestParam("id") Long id,
                           @Valid @RequestBody UserDTO userDTO,
                           BindingResult result)
            throws BadRequestParameters {

        if(result.hasErrors()) {
            log.warn("Exception thrown then trying to update user - [{}, {}], {} errors found",
                    userDTO.getFirstName(), userDTO.getLastName(), result.getErrorCount());

            throw new BadRequestParameters("Errors appears at fields then validating form.", result.getFieldErrors());
        }

        User user = userService.getById(id);

        if(user == null) {
            throw new BadRequestParameters("User with id - " + id + ", was not found");
        }

        return userService.updateUser(user, userDTO);
    }

    @DeleteMapping(params = {"id"})
    public void deleteUser(@RequestParam("id") Long id) throws BadRequestParameters {

        User user = userService.getById(id);

        if(user == null){
            throw new BadRequestParameters("User with id - " + id + ", was not found");
        }

        userService.deleteUser(user);
    }

}
