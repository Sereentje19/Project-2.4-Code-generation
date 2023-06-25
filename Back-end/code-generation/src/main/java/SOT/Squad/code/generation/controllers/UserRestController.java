package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.exceptions.UserGetException;
import SOT.Squad.code.generation.exceptions.UserUpdateException;
import SOT.Squad.code.generation.exceptions.WrongPincodeException;
import SOT.Squad.code.generation.jwt.JWTTokenProvider;
import SOT.Squad.code.generation.models.dto.EditUserRequestDTO;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.CurrentUserResponseDTO;
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
    public ResponseEntity<?> getAllUsers() {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.getAllUserIdsAndNames());
        } catch (UserGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/dropdown") //Employee
    public ResponseEntity<?> getAllUserIdsAndNames() {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.getAllUserIdsAndNames());
        } catch (UserGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
    public ResponseEntity<?> getUser(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/current") //Employee & Customer
    public ResponseEntity<?> getUserOnUsername() {
        try {
            String username = keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (UserGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/currentUser") //Employee & Customer
    public ResponseEntity<?> getUserObjectOnUsername() {
        try {
            String username = keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.getUserObjecttByUsername(username));
        } catch (UserGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}") //Employee & Customer
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody EditUserRequestDTO user) {
        try {
            keyProvider.decodeJWT();
            user.setId(id);
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (UserUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pincode/{pincode}") //Employee & Customer
    public ResponseEntity<?> checkPincode(@PathVariable String pincode) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(userService.checkPincode(pincode));
        } catch (WrongPincodeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
