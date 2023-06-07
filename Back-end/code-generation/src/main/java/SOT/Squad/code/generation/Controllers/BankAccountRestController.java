package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.DTO.IbanAndNameDTO;
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

    @GetMapping("/{id}") //Employee & Customer
    public BankAccount getAccountById(@PathVariable long id) {
        keyProvider.decodeJWT();
        return bankAccountService.getBankAccountById(id);
    }
    @GetMapping("/iban/{iban}") //Employee & Customer
    public BankAccount getAccountByIban(@PathVariable String iban) {
        keyProvider.decodeJWT();
        return bankAccountService.getBankAccountByIban(iban);
    }

    @PutMapping //Employee
    public boolean deleteBankAccount(@RequestBody BankAccount bankAccount) {
        keyProvider.decodeJWT();
        bankAccountService.deleteBankAccount(bankAccount);
        return true;
    }

    @PostMapping //Employee & Customer
    public BankAccount addBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.addBankAccount(bankAccount);
    }

    @GetMapping("/All") //Employee
    public List<IbanAndNameDTO> getAllNameAndIban() {
        keyProvider.decodeJWT();
        return bankAccountService.getAllNameAndIban();
    }

    @PutMapping("/change/{id}") //Employee & Customer
    public BankAccount updateBankAccount(@PathVariable long id,@RequestBody BankAccount bankAccount) {
        keyProvider.decodeJWT();
        return bankAccountService.updateBankAccount(bankAccount, id);
    }

}
