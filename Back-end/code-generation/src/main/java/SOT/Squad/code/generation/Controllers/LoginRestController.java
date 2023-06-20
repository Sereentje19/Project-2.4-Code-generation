package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.jwt.JWTTokenProvider;
import SOT.Squad.code.generation.models.dto.LoginRequestDTO;
import SOT.Squad.code.generation.models.dto.LoginResponseDTO;
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
            LoginResponseDTO response = loginService.login(requestDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponseDTO("Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponseDTO("An error occurred"));
        }
    }

}
