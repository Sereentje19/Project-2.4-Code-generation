package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserRestControllerTest {


    private UserService userService = new UserService();
    @Test
    void getAllUsers() {
        assertNotNull(userService.getAllUsers(),"users was null");
    }

    @Test
    void getUser() {
        assertNotNull(userService.getUser(1),"user was null");
    }

    @Test
    void addUser() {
        assertNotNull(userService.addUser(
                new User(0,"Thijs", "Moerland", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null)
        ),"user was null");
    }

    @Test
    void updateUser() {
        assertNotNull(userService.updateUser(
                new User(1,"Thijs", "Moerland", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null)
        ),"user was null");
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1);
        assertNull(userService.getUser(1),"user was not null");
    }
}