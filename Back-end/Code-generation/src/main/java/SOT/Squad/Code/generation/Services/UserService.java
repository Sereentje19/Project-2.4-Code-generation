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
        users.add(new Users(1, "Thijs", "Moerland",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
        users.add(new Users(1, "Serena", "Kenter",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
        users.add(new Users(1, "Omar", "Al Sayasna",681340798,"680942@student.inholland.nl","Söderblomstraat",53,"2131GB","Hoofddorp"));
    }
}
