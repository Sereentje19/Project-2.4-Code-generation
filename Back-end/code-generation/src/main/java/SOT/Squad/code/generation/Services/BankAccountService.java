package SOT.Squad.code.generation.Services;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountRepository.findAll();
    }

    public BankAccount addBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount updateBankAccount(BankAccount bankAccount, long id) {
        bankAccount.setId(id);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount getBankAccountByIban(String iban) {
        return (BankAccount)bankAccountRepository.findByIban(iban);
    }

    public BankAccount getBankAccountById(long id) {
        return (BankAccount)bankAccountRepository.getAllById(id);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccount.setDisabled(true);
        bankAccountRepository.save(bankAccount);
    }
}
