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

    @PutMapping("/{id}") //Employee & Customer
    public BankAccount updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable long id) {
        return bankAccountService.updateBankAccount(bankAccount, id);
    }

    @GetMapping("/{id}") //Employee & Customer
    public BankAccount getAccountById(@PathVariable long id) {
        keyProvider.decodeJWT();
        return bankAccountService.getBankAccountById(id);
    }

    @PutMapping //Employee
    public boolean deleteBankAccount(@RequestBody BankAccount bankAccount) {
        keyProvider.decodeJWT();
        bankAccountService.deleteBankAccount(bankAccount);
        return true;
    }

    @PutMapping("changebalance/{id}") //Employee
    public BankAccount changeBalance(@RequestBody BankAccount bankAccount, @PathVariable long id) {
        keyProvider.decodeJWT();
        return bankAccountService.updateBankAccount(bankAccount, id);
    }

}
