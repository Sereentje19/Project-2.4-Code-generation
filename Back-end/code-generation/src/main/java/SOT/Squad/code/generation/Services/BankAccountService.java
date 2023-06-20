package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.generators.IbanGenerator;
import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
import SOT.Squad.code.generation.models.dto.BankAccountInfoDTO;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.repositories.BankAccountRepository;
import SOT.Squad.code.generation.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount addBankAccount(BankAccount bankAccount) {
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        savedBankAccount.setId(bankAccount.getId());

        if (bankAccount.getUserId() != 0) { // Add bank account to user
            User user = userRepository.findById(bankAccount.getUserId()).get();

                if(user.getBankAccountList() == null && bankAccount.getIban() == null) {
                    //Generate iban
                    IbanGenerator generator = new IbanGenerator(bankAccountRepository);
                    bankAccount.setIban(generator.getGeneratedIban());
                }
                else if(bankAccount.getIban() == null && !user.getBankAccountList().isEmpty()) {
                    Long bankAccountId = user.getBankAccountList().get(0);
                    Optional<BankAccount> optionalAccount = bankAccountRepository.findById(bankAccountId);

                    if (optionalAccount.isPresent()) {
                        BankAccount account = optionalAccount.get();
                        bankAccount.setIban(account.getIban());
                    }
                }
                
            //Add bank account to user
            List<Long> bankAccountList = user.getBankAccountList();
            if (!bankAccountList.contains(savedBankAccount.getId())) {
                bankAccountList.add(savedBankAccount.getId());
            }

            user.setBankAccountList(bankAccountList);
            userRepository.save(user);
        }

        //Check if AccountType is not empty
        if (savedBankAccount.getAccountType() == null) {
            throw new BankAccountCreateException("Account type is not yet selected");
        }

        return savedBankAccount;
    }

    public BankAccount updateBankAccount(BankAccount bankAccount, long id) {
        bankAccount.setId(id);
        return bankAccountRepository.save(bankAccount);
    }
    public BankAccount getBankAccountById(long id) {
        return bankAccountRepository.getAllById(id);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccount.setDisabled(true);
        bankAccountRepository.save(bankAccount);
    }

    public List<BankDropDownDTO> getAllNameAndIban(List<BankAccount> bankList) {
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
        return bankAccountRepository.getAllByUserId(id);
    }

    public BankAccount getBankAccountByIban(String iban) {
        return bankAccountRepository.findFirstByIban(iban);
    }
}
