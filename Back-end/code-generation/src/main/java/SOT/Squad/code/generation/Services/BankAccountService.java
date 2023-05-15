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
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public List<BankAccount> getUsersBankAccounts(long id) {
        return (List<BankAccount>)bankAccountRepository.getAllByUserId(id);
    }

    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountRepository.findAll();
    }

    public BankAccount getBankAccountByIbanAndType(String iban, String accountType) {
        return bankAccountRepository.getByIbanAndAccountType(iban, accountType);
    }
    public BankAccount addBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(long id) {
        bankAccountRepository.deleteById(id);
    }

    public BankAccount putByIbanAndAccountType(String iban, String accountType, double amount) {
        BankAccount bankAccount = bankAccountRepository.getByIbanAndAccountType(iban, accountType);
//        bankAccount.setBalance(bankAccount.getBalance() + amount);
        return bankAccountRepository.save(bankAccount);
    }
}
