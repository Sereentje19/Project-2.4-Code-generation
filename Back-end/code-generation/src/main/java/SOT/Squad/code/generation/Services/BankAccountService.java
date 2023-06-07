package SOT.Squad.code.generation.Services;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.DTO.IbanAndNameDTO;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import SOT.Squad.code.generation.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private UserService userService;
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

    public List<IbanAndNameDTO> getAllNameAndIban() {
        List<BankAccount> bankList = (List<BankAccount>) bankAccountRepository.findAll();
        List<IbanAndNameDTO> dtoList = new ArrayList<>();
        for (BankAccount bankAccount : bankList) {
            IbanAndNameDTO ibanAndNameDTO = new IbanAndNameDTO();
            ibanAndNameDTO.setIban(bankAccount.getIban());
            User user = (User)userService.getUser(bankAccount.getUserId());
            ibanAndNameDTO.setName(user.getFirstName() + " " + user.getLastName());
            dtoList.add(ibanAndNameDTO);
        }
        return dtoList;
    }
}
