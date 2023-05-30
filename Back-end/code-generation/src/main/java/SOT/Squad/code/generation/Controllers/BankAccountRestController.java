package SOT.Squad.code.generation.Controllers;

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
    private BankAccountService bankAccountService;

    @GetMapping //Employee
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @PostMapping //Employee & Customer
    public BankAccount addBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.addBankAccount(bankAccount);
    }

    @PutMapping("/{iban}") //Employee & Customer
    public BankAccount updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable String iban) {
        return bankAccountService.updateBankAccount(bankAccount, iban);
    }

    @GetMapping("/{iban}") //Employee & Customer
    public List<BankAccount> getAccountByIban(@PathVariable String id) {
        return bankAccountService.getBankAccountByIban(id);
    }

    @DeleteMapping("/{iban}") //Employee
    public void deleteBankAccount(@PathVariable String iban) {
        bankAccountService.deleteBankAccount(iban);
    }
}
