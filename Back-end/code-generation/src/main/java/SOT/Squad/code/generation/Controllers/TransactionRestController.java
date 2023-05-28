package SOT.Squad.code.generation.Controllers;

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



    @PostMapping //Employee & Customer
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.AddTransaction(transaction);
    }

    @GetMapping() //Employee
    public List<Transaction> getAllTransactions() {
        return transactionService.GetAllTransactions();
    }

    @GetMapping("/{iban}") //Employee & Customer
    public List<Transaction> getTransactionsByIban(@PathVariable String iban) {
        return transactionService.GetTransactionsByIban(iban);
    }
    @GetMapping("/{id}") //Employee & Customer
    public Transaction getTransactionById(@PathVariable long id) {
        return transactionService.GetTransactionById(id);
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
