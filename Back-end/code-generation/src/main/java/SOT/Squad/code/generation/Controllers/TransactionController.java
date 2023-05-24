package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping()
    public List<Transaction> getAllTransactions() {
        return transactionService.GetAllTransactions();
    }
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable long id) {
        return transactionService.GetTransactionById(id);
    }
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.AddTransaction(transaction);
    }

    @GetMapping("/bank/{string}")
    public List<Transaction>GetAllByIban(@PathVariable String string){
        return transactionService.GetAllByIban(string);
    }

}
