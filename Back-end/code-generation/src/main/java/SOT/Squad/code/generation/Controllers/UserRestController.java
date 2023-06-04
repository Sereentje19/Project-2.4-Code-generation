package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserService;
import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    JWTKeyProvider keyProvider;

    @GetMapping() //Employee
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping //Employee
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}") //Employee
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}") //Employee & Customer
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/current") //Employee & Customer
    public User getUserOnUsername() {
        String username = keyProvider.decodeJWT();
        return userService.getUserByUsername(username);

    }

    @PutMapping("/{id}") //Employee & Customer
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        keyProvider.decodeJWT();
        user.setId(id);
        return userService.updateUser(user);
    }

    @GetMapping("/pincode/{pincode}") //Employee & Customer
    public User checkPincode(@PathVariable String pincode) {

        keyProvider.decodeJWT();
        return userService.checkPincode(pincode);
    }



}
