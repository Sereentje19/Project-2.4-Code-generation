package SOT.Squad.code.generation.Services;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.DTO.BankDropDownDTO;
import SOT.Squad.code.generation.Models.DTO.BankAccountInfoDTO;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public BankAccount getBankAccountById(long id) {
        return (BankAccount)bankAccountRepository.getAllById(id);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccount.setDisabled(true);
        bankAccountRepository.save(bankAccount);
    }

    public List<BankDropDownDTO> getAllNameAndIban(List<BankAccount> bankList) {
//        List<BankAccount> bankList = (List<BankAccount>) bankAccountRepository.findAll();
        List<BankDropDownDTO> dtoList = new ArrayList<>();

        for (int i = 1; i < bankList.size(); i++) {
            BankDropDownDTO ibanAndNameDTO = new BankDropDownDTO();
            ibanAndNameDTO.setIban(bankList.get(i).getIban());
            User user = (User)userService.getUser(bankList.get(i).getUserId());
            ibanAndNameDTO.setName(user.getFirstName() + " " + user.getLastName());
            ibanAndNameDTO.setAccountType(bankList.get(i).getAccountType());
            ibanAndNameDTO.setId(bankList.get(i).getId());
            dtoList.add(ibanAndNameDTO);
        }
        return dtoList;
    }

    public List<BankDropDownDTO> getAllNameAndIbanFirst(List<BankAccount> bankList) {
//        List<BankAccount> bankList = (List<BankAccount>) bankAccountRepository.findAll();
        List<BankDropDownDTO> dtoList = new ArrayList<>();

        for (int i = 0; i < bankList.size(); i++) {
            BankDropDownDTO ibanAndNameDTO = new BankDropDownDTO();
            ibanAndNameDTO.setIban(bankList.get(i).getIban());
            User user = (User)userService.getUser(bankList.get(i).getUserId());
            ibanAndNameDTO.setName(user.getFirstName() + " " + user.getLastName());
            ibanAndNameDTO.setAccountType(bankList.get(i).getAccountType());
            ibanAndNameDTO.setId(bankList.get(i).getId());
            dtoList.add(ibanAndNameDTO);
        }
        return dtoList;
    }

    public BankAccountInfoDTO getBankAccountInfo(long id) {
        BankAccount bankList = bankAccountRepository.getAllById(id);

        BankAccountInfoDTO bankAccountInfoDTO = new BankAccountInfoDTO();
        bankAccountInfoDTO.setId(bankList.getId());
        bankAccountInfoDTO.setIban(bankList.getIban());
        bankAccountInfoDTO.setAccountType(bankList.getAccountType());
        bankAccountInfoDTO.setCurrencies(bankList.getCurrencies());

        return bankAccountInfoDTO;
    }

    public List<BankAccount> getAllBankAccountsByUserId(long id) {
        return (List<BankAccount>) bankAccountRepository.getAllByUserId(id);
    }

    public BankAccount getBankAccountByIban(String iban) {
        return (BankAccount) bankAccountRepository.findFirstByIban(iban);
    }
}
