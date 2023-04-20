package SOT.Squad.code.generation.Services;

import SOT.Squad.code.generation.Models.Users;
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

    public Users updateUser(Users user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return user;
            }
        }
        return null;
    }

    public Users deleteUser(Users user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.remove(i);
                return user;
            }
        }
        return null;
    }

    public UserService() {
        users.add(new Users(1, "Thijs", "Moerland",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
        users.add(new Users(2, "Serena", "Kenter",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
        users.add(new Users(3, "Omar", "Al Sayasna",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
    }
}
