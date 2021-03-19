package test.Library.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import test.Library.dto.UserDTO;
import test.Library.model.EntityStatus;
import test.Library.model.User;
import test.Library.repository.UserRepository;
import test.Library.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void addUserTest(){

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Name");
        userDTO.setLastName("Surname");

        User user = userService.addUser(userDTO);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getFirstName(),userDTO.getFirstName());
        Assert.assertEquals(user.getLastName(),userDTO.getLastName());
    }

    @Test
    public void updateUserTest(){

        UserDTO userDTO = new UserDTO();
        User oldUser = new User();

        userDTO.setFirstName("Name");
        userDTO.setLastName("Surname");

        User user = userService.updateUser(oldUser, userDTO);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getFirstName(),userDTO.getFirstName());
        Assert.assertEquals(user.getLastName(),userDTO.getLastName());
    }

    @Test
    public void deleteUserTest(){

        User user = new User();

        userService.deleteUser(user);

        Assert.assertEquals(user.getStatus(), EntityStatus.DELETED);
    }


}
