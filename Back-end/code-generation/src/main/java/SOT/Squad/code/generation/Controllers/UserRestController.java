package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserRestController extends Controller{

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) throws Exception {
        try {
            User currentUser = userService.getByUsernameAndPassword(user.getUsername(), user.getPassword());

            if (currentUser == null) {
                throw new Exception("Incorrect username or password.");
            }
            
            return generateJwt(currentUser);

        }catch(Exception exception){
            throw new Exception(exception.getMessage());
        }

    }


    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

//    @PostMapping("/login")
//    public User getByUsernameAndPassword(@RequestBody User user) {
//        return userService.getByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }





        private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        private static final String ISSUER = "THE_ISSUER";
        private static final String AUDIENCE = "THE_AUDIENCE";

        public static Map<String, Object> generateJwt(User user) {
            long issuedAtMillis = System.currentTimeMillis();
            long expirationMillis = issuedAtMillis + 9000 * 1000; // 9000 seconds

            JwtBuilder jwtBuilder = Jwts.builder()
                    .setIssuer(ISSUER)
                    .setAudience(AUDIENCE)
                    .setIssuedAt(new Date(issuedAtMillis))
                    .setNotBefore(new Date(issuedAtMillis))
                    .setExpiration(new Date(expirationMillis))
                    .claim("data", user)
                    .signWith(SECRET_KEY);

            String jwt = jwtBuilder.compact();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successful login.");
            response.put("jwt", jwt);
            response.put("username", user.getUsername());
            response.put("expireAt", expirationMillis);

            return response;
        }


}
