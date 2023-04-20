package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.Users;
import SOT.Squad.code.generation.Services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/restusers")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/restusers")
    public Users addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }

    @PutMapping("/restusers")
    public Users updateUser(@RequestBody Users user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/restusers")
    public Users deleteUser(@RequestBody Users user) {
        return userService.deleteUser(user);
    }

}
