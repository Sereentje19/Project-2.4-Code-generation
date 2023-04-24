package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DataSeeder implements ApplicationRunner {
    @Autowired
    private UserService userService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addUser(new User(1,"Thijs", "Moerland", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));
        userService.addUser(new User(2,"Omar", "Al Sayasna", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));
        userService.addUser(new User(3,"Serena", "Kenter", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));

    }
}
