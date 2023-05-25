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

    @GetMapping()
    public List<Transaction> getAllTransactions() {
        return transactionService.GetAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable long id) {
        return transactionService.GetTransaction(id);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.AddTransaction(transaction);
    }

}
