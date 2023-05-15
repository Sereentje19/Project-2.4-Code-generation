package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.BankAccountService;
import SOT.Squad.code.generation.Services.TransactionService;
import SOT.Squad.code.generation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

@Component
public class DataSeeder implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addUser(new User(0,"Thijs", "Moerland", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));
        userService.addUser(new User(0,"Omar", "Al Sayasna", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));
        userService.addUser(new User(0,"Serena", "Kenter", 064567,"Moerland8","123street",53,"2131GB","hoofddorp", null));

        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456789", "CURRENT", "Euro", 1000, 1));
        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456786", "SAVING", "Euro", 2000, 1));
        bankAccountService.addBankAccount(new BankAccount(0, "NL12INHO0123456787", "CURRENT", "Euro", 100, 2));

        transactionService.AddTransaction(new Transaction(0, "test Transaction", 100, "CURRENT", "CURRENT", "NL12INHO0123456789", "NL12INHO0123456787"));
        transactionService.AddTransaction(new Transaction(0, "test Transaction", 100, "CURRENT", "CURRENT", "NL12INHO0123456786", "NL12INHO0123456786"));
        transactionService.AddTransaction(new Transaction(0, "test Transaction", 100, "CURRENT", "CURRENT", "NL12INHO0123456787", "NL12INHO0123456789"));
    }
}
