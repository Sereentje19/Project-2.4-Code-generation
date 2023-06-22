package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.jwt.JWTTokenProvider;
import SOT.Squad.code.generation.models.DTO.EditUserRequestDTO;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.UserDropDownDTO;
import SOT.Squad.code.generation.services.UserService;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
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
        try {
            keyProvider.decodeJWT();
            return userService.getAllUsers();

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/dropdown") //Employee
    public List<UserDropDownDTO> getAllUserIdsAndNames() {
        try {
            keyProvider.decodeJWT();
            return userService.getAllUserIdsAndNames();

        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping //Employee
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            keyProvider.decodeJWT();
            userService.checkInputFieldsNotEmpty(user); // Check if input fields are empty
            userService.checkPasswordStrength(user.getPassword()); // Check password strength
            return ResponseEntity.ok(userService.addUser(user));
        } catch (UserCreateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/register")//Employee
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.checkInputFieldsNotEmpty(user); // Check if input fields are empty
            userService.checkPasswordStrength(user.getPassword()); // Check password strength
            return ResponseEntity.ok(userService.addUser(user));
        } catch (UserCreateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}") //Employee & Customer
    public User getUser(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();

            return userService.getUser(id);
        }catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/current") //Employee & Customer
    public User getUserOnUsername() {
        try {
            String username = keyProvider.decodeJWT();
            return userService.getUserByUsername(username);
        }catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/currentUser") //Employee & Customer
    public User getUserObjectOnUsername() {
        try {
            String username = keyProvider.decodeJWT();
            return userService.getUserObjecttByUsername(username);
        }catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}") //Employee & Customer
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody EditUserRequestDTO user) {
        try {
            keyProvider.decodeJWT();
            user.setId(id);
            return ResponseEntity.ok(userService.updateUser(id, user));

        }catch (Exception e) {
            return null;
        }

    }

    @GetMapping("/pincode/{pincode}") //Employee & Customer
    public boolean checkPincode(@PathVariable String pincode) {
        try {
            keyProvider.decodeJWT();
            return userService.checkPincode(pincode);
        }catch (Exception e) {
            return false;
        }
    }
}
