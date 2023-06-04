package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bankaccounts")
public class BankAccountRestController {

    @Autowired
    JWTKeyProvider keyProvider;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping //Employee
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @PostMapping //Employee & Customer
    public BankAccount addBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.addBankAccount(bankAccount);
    }

//    @PutMapping("/{iban}") //Employee & Customer
//    public BankAccount updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable String iban) {
//        return bankAccountService.updateBankAccount(bankAccount, iban);
//    }

    @GetMapping("/{iban}") //Employee & Customer
    public List<BankAccount> getAccountByIban(@PathVariable String id) {
        keyProvider.decodeJWT();
        return bankAccountService.getBankAccountByIban(id);
    }

    @GetMapping("/info/{id}") //Employee & Customer
    public BankAccount getAccountById(@PathVariable String id) {
        keyProvider.decodeJWT();
        return bankAccountService.getBankAccountById(id);
    }

    @PutMapping("/delete") //Employee
    public boolean deleteBankAccount(@RequestBody BankAccount bankAccount) {
        keyProvider.decodeJWT();
        bankAccountService.deleteBankAccount(bankAccount);
        return true;
    }
}
