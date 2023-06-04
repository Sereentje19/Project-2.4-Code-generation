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
        BankAccount bankacc2 = new BankAccount(2, "NL12INHO0123456788",  2000, 1, false, List.of(Currency.POUND), List.of(AccountType.SAVINGS));
        BankAccount bankacc3 = new BankAccount(3, "NL12INHO0123456786",  100, 2, false, List.of(Currency.DOLLAR), List.of(AccountType.DEPOSIT));
        bankAccountService.addBankAccount(bankacc1);
        bankAccountService.addBankAccount(bankacc2);
        bankAccountService.addBankAccount(bankacc3);

        transactionService.AddTransaction(new Transaction(1, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456787", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(2, "test", 100,  "NL12INHO0123456788", "NL12INHO0123456789", List.of(AccountType.CURRENT), List.of(AccountType.DEPOSIT), "kenmerk"));
        transactionService.AddTransaction(new Transaction(3, "test", 100,  "NL12INHO0123456787", "NL12INHO0123456788", List.of(AccountType.CURRENT), List.of(AccountType.CURRENT), "kenmerk"));
        transactionService.AddTransaction(new Transaction(4, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456787", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(5, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456785", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(6, "test", 400,  "NL12INHO0123456789", "NL12INHO0123456787", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(7, "test", 90,  "NL12INHO0123456789", "NL12INHO0123456789", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(8, "test", 10,  "NL12INHO0123456789", "NL12INHO0123456782", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(9, "test", 1.8,  "NL12INHO0123456789", "NL12INHO0123456781", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        transactionService.AddTransaction(new Transaction(10, "test", 10.20,  "NL12INHO0123456789", "NL12INHO0123456787", List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));

        userService.addUser(new User(1, "thijs", "moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER), "0000"));
        userService.addUser(new User(2, "om", "al", "Omar", "Al Sayasna", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",  List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER),"1234"));
        userService.addUser(new User(3, "serena", "kenter", "Serena", "Kenter", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc1.getId(), bankacc2.getId()), List.of(Role.CUSTOMER),"4321"));

    }
}
