package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.UserDropDownDTO;
import SOT.Squad.code.generation.repositories.UserRepository;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    private List<User> users = new ArrayList<>();

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findUserByUsername(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public void checkPasswordStrength(String passw) {
        boolean hasUppercase = !passw.equals(passw.toLowerCase());
        boolean hasLowercase = !passw.equals(passw.toUpperCase());
        boolean hasSpecialCharacters = !passw.matches("[A-Za-z0-9 ]*");
        boolean hasDigits = passw.matches(".*\\d.*");

        if (!hasUppercase) {
            throw new UserCreateException("Password must contain at least one uppercase letter.");
        }else  if (!hasLowercase) {
            throw new UserCreateException("Password must contain at least one lowercase letter.");
        }else if (!hasSpecialCharacters) {
            throw new UserCreateException("Password must contain at least one special character.");
        }else if (!hasDigits) {
            throw new UserCreateException("Password must contain at least one digit.");
        }
    }

    public void checkInputFieldsNotEmpty(User user) {
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new UserCreateException("Password has to be at least 8 characters.");
        } else if (user.getUsername() == null || user.getUsername().length() < 5) {
            throw new UserCreateException("Username has to be at least 5 characters.");
        } else if (user.getPincode() == null || !user.getPincode().matches("\\d{4}")) {
            throw new UserCreateException("Pincode has to be exactly 4 numbers.");
        } else if (!user.getEmail().contains("@")) {
            throw new UserCreateException("Please enter a valid email.");
        } else if (String.valueOf(user.getPhoneNumber()) == null || String.valueOf(user.getPhoneNumber()).length() != 10) {
            throw new UserCreateException("Phonenumber has to be exactly 10 numbers");
        } else if (user.getFirstName() == null || user.getLastName() == null
                || user.getPostalCode() == null || user.getCity() == null
                || user.getStreet() == null || !String.valueOf(user.getHouseNumber()).matches("\\d+")) {
            throw new UserCreateException("Please fill all fields before saving all changes.");
        }
    }


    public User addUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new UserCreateException("Username is already taken.");
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<UserDropDownDTO> getAllUserIdsAndNames() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserDropDownDTO> userDropDownDTOList = new ArrayList<>();

        for (User user : users) {
            UserDropDownDTO userDropDownDTO = new UserDropDownDTO();
            userDropDownDTO.setId(user.getId());
            userDropDownDTO.setFirstName(user.getFirstName());
            userDropDownDTO.setLastName(user.getLastName());
            userDropDownDTOList.add(userDropDownDTO);
        }

        return userDropDownDTOList;
    }
    public User getUser(long id) {
        return userRepository.findById(id).get();
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public boolean checkPincode(String pincode) {
        User user = userRepository.findUserByPincode(pincode);
        if(user != null || user.getId() != 0){
            return true;
        }
        return false;
    }
}
