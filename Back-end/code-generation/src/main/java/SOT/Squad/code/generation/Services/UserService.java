package SOT.Squad.code.generation.Services;

import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }

//    public List<User> getAllUsers(int limit, int offset) {
//        return (List<User>)userRepository.getAllUsersWithLimitAndOffset(limit, offset);
//    }

    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
    public User getByUsernameAndPassword(String username, String password)
    {

        return userRepository.getByUsernameAndPassword(username, password);
    }

}
