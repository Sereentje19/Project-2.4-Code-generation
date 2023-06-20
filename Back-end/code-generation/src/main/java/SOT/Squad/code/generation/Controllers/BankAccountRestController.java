package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
import SOT.Squad.code.generation.models.dto.BankAccountInfoDTO;
import SOT.Squad.code.generation.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
    public List<BankDropDownDTO> getAllBankAccountsByUserId(@PathVariable long id) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = bankAccountService.getAllBankAccountsByUserId(id);
            return bankAccountService.getAllNameAndIbanFirst(bankList);
        }catch (Exception e) {
            return null;
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
    public List<BankDropDownDTO> getBankAccountByIban(@PathVariable String iban) {
        try {
            keyProvider.decodeJWT();
            List<BankAccount> bankList = new ArrayList<>();
            bankList.add(bankAccountService.getBankAccountByIban(iban));
            return bankAccountService.getAllNameAndIbanFirst(bankList);
        }catch (Exception e) {
            return null;
        }

    }

}
