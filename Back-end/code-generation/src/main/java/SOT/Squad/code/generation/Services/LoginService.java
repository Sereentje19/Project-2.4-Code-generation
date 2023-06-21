package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.InActiveAccountException;
import SOT.Squad.code.generation.exceptions.InvalidCredentialsException;
import SOT.Squad.code.generation.jwt.JWTTokenProvider;
import SOT.Squad.code.generation.models.dto.LoginRequestDTO;
import SOT.Squad.code.generation.models.dto.LoginResponseDTO;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        User user = userRepository.findUserByUsername(requestDTO.getUsername()).orElseThrow(() -> new InvalidCredentialsException("Username is incorrect"));
        if (user.isInActive()) {
            throw new InActiveAccountException("User is inactive");
        } else if (encoder.matches(requestDTO.getPassword(), user.getPassword())) {
            String token = tokenProvider.createToken(user.getUsername(), user.getRoles());
            LoginResponseDTO response = new LoginResponseDTO(token);
            response.setToken(token);
            return response;
        }
        throw new InvalidCredentialsException("Password is incorrect");
    }

}
