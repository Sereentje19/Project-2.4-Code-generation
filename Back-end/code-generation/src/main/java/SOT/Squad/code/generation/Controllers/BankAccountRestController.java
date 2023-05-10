package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bankaccounts")
public class BankAccountRestController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/{id}")
    public List<BankAccount> getAllAccounts(@PathVariable long id) {
        return bankAccountService.getUsersBankAccounts(id);
    }

    @GetMapping
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }
}
