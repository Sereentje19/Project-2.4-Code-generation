package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.Models.*;
import SOT.Squad.code.generation.Services.BankAccountService;
import SOT.Squad.code.generation.Services.TransactionService;
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

    @Autowired
    private TransactionService transactionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BankAccount bankacc1 = new BankAccount(1, "NL12INHO0123456789",   1000, 1, false, List.of(Currency.EURO), List.of(AccountType.CURRENT));
        BankAccount bankacc2 = new BankAccount(2, "NL12INHO0123456788",  2000, 1, false, List.of(Currency.EURO), List.of(AccountType.SAVINGS));
        BankAccount bankacc3 = new BankAccount(3, "NL12INHO0123456787",  100, 2, false, List.of(Currency.EURO), List.of(AccountType.DEPOSIT));
        bankAccountService.addBankAccount(bankacc1);
        bankAccountService.addBankAccount(bankacc2);
        bankAccountService.addBankAccount(bankacc3);

        transactionService.AddTransaction(new Transaction(1, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456787", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS)));
        transactionService.AddTransaction(new Transaction(2, "test", 100,  "NL12INHO0123456788", "NL12INHO0123456789", List.of(AccountType.CURRENT), List.of(AccountType.DEPOSIT)));
        transactionService.AddTransaction(new Transaction(3, "test", 100,  "NL12INHO0123456787", "NL12INHO0123456788", List.of(AccountType.CURRENT), List.of(AccountType.CURRENT)));

        userService.addUser(new User(1, "thijs", "moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER)));
        userService.addUser(new User(2, "om", "al", "Omar", "Al Sayasna", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",  List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER)));
        userService.addUser(new User(3, "serena", "kenter", "Serena", "Kenter", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER)));

    }
}
