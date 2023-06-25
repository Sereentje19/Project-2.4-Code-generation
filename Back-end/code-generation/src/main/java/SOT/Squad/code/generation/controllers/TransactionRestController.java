package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.*;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.dto.TransactionOverViewDTO;
import SOT.Squad.code.generation.models.dto.TransactionRequestDTO;
import SOT.Squad.code.generation.models.dto.TransactionResponseDTO;
import SOT.Squad.code.generation.models.Transaction;
import SOT.Squad.code.generation.models.dto.withdrawOrDepositDTO;
import SOT.Squad.code.generation.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    JWTKeyProvider keyProvider;

    @PostMapping //Employee & Customer
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(transactionService.validateTransaction(transactionRequestDTO));
        }catch (TransactionCreateException | BankAccountUpdateException | UserUpdateException | ValidateTransactionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/withdrawOrDeposit")
    public ResponseEntity<?> withdrawOrDeposit(@RequestBody withdrawOrDepositDTO withdrawOrDeposit) {
        try {
            keyProvider.decodeJWT();

            return ResponseEntity.ok(transactionService.validateWithdrawOrDeposit(withdrawOrDeposit));
        }catch (ValidateWithdrawOrTransactionException | BankAccountUpdateException | UserUpdateException e) {
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
    public TransactionOverViewDTO getTransactionById(@PathVariable long id) {
        try{
            keyProvider.decodeJWT();
            return transactionService.GetTransactionById(id);
        }catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/account/{id}") //Employee & Customer
    public List<TransactionResponseDTO> findByBankAccountAndAccountType(
            @PathVariable long id,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam("operator") String operator,
            @RequestParam("searchField") int searchField) {
        try{
            keyProvider.decodeJWT();
            return transactionService.findBankAccountResponse(id, startDate, endDate, operator, searchField);
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
