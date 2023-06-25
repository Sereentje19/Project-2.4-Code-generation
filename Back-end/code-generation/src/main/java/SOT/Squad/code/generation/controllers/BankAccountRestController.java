package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.BankAccountGetException;
import SOT.Squad.code.generation.exceptions.BankAccountUpdateException;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
import SOT.Squad.code.generation.models.dto.BankAccountInfoDTO;
import SOT.Squad.code.generation.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bankaccounts")
public class BankAccountRestController {

    @Autowired
    JWTKeyProvider keyProvider;

    @Autowired
    private BankAccountService bankAccountService;


    @PostMapping // Employee & Customer
    public ResponseEntity<?> addBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.addBankAccount(bankAccount));
        } catch (BankAccountCreateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


    @GetMapping("/{id}") //Employee & Customer
    public ResponseEntity<?> getAccountById(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/dto/{id}") //Employee & Customer
    public ResponseEntity<?> getAccountDtoById(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = new ArrayList<>();
            bankList.add(bankAccountService.getBankAccountById(id));
            return ResponseEntity.ok(bankAccountService.getAllNameAndIbanFirst(bankList));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping //Employee
    public ResponseEntity<?> getAllBankAccounts() {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping //Employee
    public ResponseEntity<?> deleteBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            keyProvider.decodeJWT();
            bankAccountService.deleteBankAccount(bankAccount);
            return ResponseEntity.ok(true);
        }catch (BankAccountUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/change/{id}") //Employee & Customer
    public ResponseEntity<?> updateBankAccount(@PathVariable long id,@RequestBody BankAccount bankAccount) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.updateBankAccount(bankAccount, id));
        }catch (BankAccountUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/userID/{id}")
    public ResponseEntity<?> getAllBankAccountsByUserId(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = bankAccountService.getAllBankAccountsByUserId(id);
            return ResponseEntity.ok(bankAccountService.getAllNameAndIbanFirst(bankList));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/info/{id}") //Employee
    public ResponseEntity<?> getBankAccountInfo(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.getBankAccountInfo(id));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/accountType/{userId}") //Employee
    public ResponseEntity<?> getAccountTypes(@PathVariable long userId) {
        try {
            keyProvider.decodeJWT();
            return ResponseEntity.ok(bankAccountService.getAccountTypes(userId));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/All") //Employee
    public ResponseEntity<?> getAllNameAndIban() {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = bankAccountService.getAllBankAccounts();
            return ResponseEntity.ok(bankAccountService.getAllNameAndIban(bankList));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/iban/{iban}") //Employee & Customer
    public ResponseEntity<?> getBankAccountByIban(@PathVariable String iban) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = new ArrayList<>();
            bankList.add(bankAccountService.getBankAccountByIban(iban));
            return ResponseEntity.ok(bankAccountService.getAllNameAndIbanFirst(bankList));
        }catch (BankAccountGetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
