package SOT.Squad.Code.generation.Services;

import SOT.Squad.Code.generation.Models.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<Users> users = new ArrayList<>();

    public List<Users> getAllUsers() {
        return users;
    }

    public Users addUser(Users user) {
        users.add(user);
        return user;
    }

    public UserService() {
        users.add(new Users(1, "Thijs", "Moerland"));
        users.add(new Users(2, "Serena", "Kenter"));
        users.add(new Users(3, "Omar", "Al Sayasna"));
}
