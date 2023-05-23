package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.Role;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.BankAccountService;
import SOT.Squad.code.generation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addUser(new User(0, "thijs", "Moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", null, List.of(Role.CUSTOMER)));
        userService.addUser(new User(0, "omar", "Al Sayasna", "Omar", "Al Sayasna", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", null, List.of(Role.CUSTOMER)));
        userService.addUser(new User(0, "serena", "kenter", "Serena", "Kenter", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", null, List.of(Role.CUSTOMER)));

        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456789", "CURRENT", "Euro", 1000, 1));
        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456788", "SAVING", "Euro", 2000, 1));
        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456787", "CURRENT", "Euro", 100, 2));

    }
}
