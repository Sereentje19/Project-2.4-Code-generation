package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    JWTKeyProvider keyProvider;

//    @RequestBody Transaction transaction
    @PostMapping //Employee & Customer
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        keyProvider.decodeJWT();
        return transactionService.AddTransaction(transaction);
    }

    @GetMapping() //Employee
    public List<Transaction> getAllTransactions() {
        return transactionService.GetAllTransactions();
    }

    @GetMapping("/{id}") //Employee & Customer
    public Transaction getTransactionById(@PathVariable long id) {
        try{
            keyProvider.decodeJWT();
            return transactionService.GetTransactionById(id);
        }catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/account/{iban}/{type}") //Employee & Customer
    public List<Transaction> findByBankAccountAndAccountType(@PathVariable String iban, @PathVariable List<AccountType> type) {
        try{
            keyProvider.decodeJWT();
            return transactionService.findByBankAccountAndAccountType(iban, type);
        }catch (Exception e) {
            return null;
        }
    }


    @PutMapping("/{id}") //Employee
    public Transaction updateTransaction(@PathVariable long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        return transactionService.UpdateTransaction(transaction);
    }

    @DeleteMapping("/{id}") //Employee
    public void deleteTransaction(@PathVariable long id) {
        transactionService.DeleteTransaction(id);
    }

}
