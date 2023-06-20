package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.repositories.UserRepository;
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

    public User addUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Username is already taken");
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
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
