package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.TransactionCreateException;
import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.dto.TransactionRequestDTO;
import SOT.Squad.code.generation.models.dto.TransactionResponseDTO;
import SOT.Squad.code.generation.models.Transaction;
import SOT.Squad.code.generation.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(transactionService.validateTransaction(transactionRequestDTO));
        }catch (TransactionCreateException | BankAccountCreateException | UserCreateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping() //Employee
    public List<Transaction> getAllTransactions() {
        try {
            keyProvider.decodeJWT();

            return transactionService.GetAllTransactions();
        }catch (Exception e) {
            return null;
        }
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
    public List<TransactionResponseDTO> findByBankAccountAndAccountType(@PathVariable String iban, @PathVariable List<AccountType> type) {
        try{
            keyProvider.decodeJWT();
            return transactionService.findBankAccountResponse(iban, type);
        }catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}") //Employee
    public Transaction updateTransaction(@PathVariable long id, @RequestBody Transaction transaction) {
        try {
            keyProvider.decodeJWT();
            transaction.setId(id);
            return transactionService.UpdateTransaction(transaction);
        }catch (Exception e) {
            return null;
        }

    }

}
