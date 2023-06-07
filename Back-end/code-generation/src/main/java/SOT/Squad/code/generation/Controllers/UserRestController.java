package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTTokenProvider;
import SOT.Squad.code.generation.Models.DTO.LoginResponseDTO;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserService;
import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @Autowired
    JWTTokenProvider tokenProvider;

    @GetMapping() //Employee
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping //Employee
    public User addUser(@RequestBody User user) {
        keyProvider.decodeJWT();
        return userService.addUser(user);
    }

//    @PostMapping("/register")//Employee
//    public User register(@RequestBody User user) {
//        return userService.addUser(user);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User addedUser = userService.addUser(user);
            String token = tokenProvider.createToken(addedUser.getUsername(), addedUser.getRoles());

            LoginResponseDTO response = new LoginResponseDTO(token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponseDTO("Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponseDTO("An error occurred"));
        }
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
