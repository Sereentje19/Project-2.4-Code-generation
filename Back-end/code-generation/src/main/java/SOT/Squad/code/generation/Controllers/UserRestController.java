package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.DTO.LoginRequestDTO;
import SOT.Squad.code.generation.Models.DTO.LoginResponseDTO;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserDetailService;
import SOT.Squad.code.generation.Services.UserService;
import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserRestController extends Controller {

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

    @GetMapping("/test") //Employee & Customer
    public User getUserOnUsername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = keyProvider.decodeJWT(request.getHeader("Authorization"));
        User user = userService.getUserByUsername(username);
        return user;
    }

    @PutMapping("/{id}") //Employee & Customer
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }



}
