package SOT.Squad.code.generation.generators;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class PasswordHasher {
    private String generatedPassword;

    public boolean checkPasswordStrength() {
        boolean hasUppercase = !generatedPassword.equals(generatedPassword.toLowerCase());
        boolean hasLowercase = !generatedPassword.equals(generatedPassword.toUpperCase());
        boolean hasSpecialCharacters = !generatedPassword.matches("[A-Za-z0-9 ]*");
        boolean hasDigits = generatedPassword.matches(".*\\d.*");

        return hasUppercase && hasLowercase && hasSpecialCharacters && hasDigits;
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }

}
