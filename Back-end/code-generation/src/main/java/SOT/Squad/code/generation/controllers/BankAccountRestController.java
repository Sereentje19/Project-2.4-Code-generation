package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.BankAccountGetException;
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
    public BankAccount getAccountById(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            return bankAccountService.getBankAccountById(id);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/dto/{id}") //Employee & Customer
    public List<BankDropDownDTO> getAccountDtoById(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = new ArrayList<>();
            bankList.add(bankAccountService.getBankAccountById(id));
//            return bankList;
            return bankAccountService.getAllNameAndIbanFirst(bankList);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping //Employee
    public List<BankAccount> getAllBankAccounts() {
        try {
            keyProvider.decodeJWT();
            return bankAccountService.getAllBankAccounts();
        }catch (Exception e) {
            return null;
        }

    }

    @PutMapping //Employee
    public boolean deleteBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            keyProvider.decodeJWT();
            bankAccountService.deleteBankAccount(bankAccount);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    @PutMapping("/change/{id}") //Employee & Customer
    public BankAccount updateBankAccount(@PathVariable long id,@RequestBody BankAccount bankAccount) {
        try {
            keyProvider.decodeJWT();
            return bankAccountService.updateBankAccount(bankAccount, id);
        }catch (Exception e) {
            return null;
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
    public BankAccountInfoDTO getBankAccountInfo(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            return bankAccountService.getBankAccountInfo(id);
        }catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/accountType/{userId}") //Employee
    public List<AccountType> getAccountTypes(@PathVariable long userId) {
        try {
            keyProvider.decodeJWT();
            return bankAccountService.getAccountTypes(userId);
        }catch (Exception e) {
            return null;
        }
    }


    @GetMapping("/All") //Employee
    public List<BankDropDownDTO> getAllNameAndIban() {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = bankAccountService.getAllBankAccounts();
            return bankAccountService.getAllNameAndIban(bankList);
        }catch (Exception e) {
            return null;
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
