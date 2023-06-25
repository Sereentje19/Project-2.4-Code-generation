package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.InActiveAccountException;
import SOT.Squad.code.generation.exceptions.InvalidCredentialsException;
import SOT.Squad.code.generation.jwt.JWTTokenProvider;
import SOT.Squad.code.generation.models.dto.LoginRequestDTO;
import SOT.Squad.code.generation.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @Autowired
    JWTTokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(loginService.login(requestDTO));
        } catch (InvalidCredentialsException | InActiveAccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
