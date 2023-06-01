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

    public BankAccount updateBankAccount(BankAccount bankAccount, String iban) {
        bankAccount.setIban(iban);
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getBankAccountByIban(String iban) {
        return (List<BankAccount>)bankAccountRepository.getAllByIban(iban);
    }

    public BankAccount getBankAccountById(String id) {
        return (BankAccount)bankAccountRepository.getAllById(id);
    }

    public void deleteBankAccount(String iban) {
        bankAccountRepository.deleteByIban(iban);
    }
}
