package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.models.*;
import SOT.Squad.code.generation.services.BankAccountService;
import SOT.Squad.code.generation.services.TransactionService;
import SOT.Squad.code.generation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

        //Bankaccounts
        BankAccount bankacc1 = new BankAccount(1, "NL01INHO0000000001",   0, 0, false, "EUR", List.of(AccountType.CURRENT), 0);
        BankAccount bankacc2 = new BankAccount(2, "NL12INHO0123456789",   1000, 1, false, "EUR", List.of(AccountType.CURRENT),10);
        BankAccount bankacc3 = new BankAccount(3, "NL12INHO0123456788",  100, 2, false, "EUR", List.of(AccountType.CURRENT),10);
        BankAccount bankacc4 = new BankAccount(4, "NL12INHO0123456787",  100, 3, false, "EUR", List.of(AccountType.CURRENT),10);
        BankAccount bankacc5 = new BankAccount(5, "NL12INHO0123456787",  100, 3, false, "EUR", List.of(AccountType.SAVINGS),10);
        BankAccount bankacc6 = new BankAccount(6, "NL12INHO0123456787",  100, 3, false, "EUR", List.of(AccountType.SAVINGS),10);

        //Users
        User user1 = new User(1, "thijs", "moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc2.getId()),false, List.of(Role.CUSTOMER), "5781",2000,300);
        User user2 = new User(2, "om", "al", "Omar", "Al Sayasna", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",  List.of(bankacc3.getId()),false, List.of(Role.CUSTOMER), "2595",2000,300);
        User user3 = new User(3, "serena", "kenter", "Serena", "Kenter", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", List.of(bankacc4.getId(), bankacc5.getId()),false, List.of(Role.EMPLOYEE), "3685",2000,300);

        //add Users
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        //add Bankaccounts
        bankAccountService.addBankAccount(bankacc1);
        bankAccountService.addBankAccount(bankacc2);
        bankAccountService.addBankAccount(bankacc3);
        bankAccountService.addBankAccount(bankacc4);
        bankAccountService.addBankAccount(bankacc5);
        bankAccountService.addBankAccount(bankacc6);

        //Transactions
        transactionService.AddTransaction(new Transaction(1, "test", 100000,  5, 6,  "kenmerk", LocalDateTime.now().minusDays(3),user1));
        transactionService.AddTransaction(new Transaction(2, "test", 1002,  3, 2, "kenmerk", LocalDateTime.now().plusDays(4),user1));
        transactionService.AddTransaction(new Transaction(3, "test", 100,  2, 3, "kenmerk", LocalDateTime.now().plusDays(4),user1));
        transactionService.AddTransaction(new Transaction(4, "test", 100,  4, 3, "kenmerk", LocalDateTime.now().plusDays(4),user1));
        transactionService.AddTransaction(new Transaction(5, "test", 100,  2, 3, "kenmerk", LocalDateTime.now().plusDays(4),user1));
        transactionService.AddTransaction(new Transaction(6, "test", 400,  4, 3, "kenmerk", LocalDateTime.now(),user1));
        transactionService.AddTransaction(new Transaction(7, "test", 90,  3, 2, "kenmerk", LocalDateTime.now(),user1));
        transactionService.AddTransaction(new Transaction(8, "test", 10,  4, 3, "kenmerk", LocalDateTime.now(),user1));
        transactionService.AddTransaction(new Transaction(9, "test", 1.8,  2, 3, "kenmerk", LocalDateTime.now(),user1));
        transactionService.AddTransaction(new Transaction(0, "test", 10.20,  2, 3, "kenmerk", LocalDateTime.now(),user1));
    }
}
