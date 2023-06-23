package SOT.Squad.code.generation.services;


import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.exceptions.TransactionCreateException;
import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.exceptions.ValidateTransactionException;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.dto.EditUserRequestDTO;
import SOT.Squad.code.generation.models.dto.TransactionRequestDTO;
import SOT.Squad.code.generation.models.dto.TransactionResponseDTO;
import SOT.Squad.code.generation.models.Transaction;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;

    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> GetAllTransactions() {
        return (List<Transaction>)transactionRepository.findAll();
    }

    public Transaction GetTransactionById(long id) {
        return transactionRepository.findById(id).get();
    }
    public List<Transaction> findByBankAccountAndAccountType(String iban, List<AccountType> accountType) {
        return transactionRepository.findByBankAccountToAndAccountTypeToInOrBankAccountFromAndAccountTypeFromIn(iban, accountType, iban, accountType);
    }

    public List<TransactionResponseDTO> findBankAccountResponse(String iban, List<AccountType> accountType) {
        List<Transaction> transactionList = transactionRepository.findByBankAccountToAndAccountTypeToInOrBankAccountFromAndAccountTypeFromIn(iban, accountType, iban, accountType);
        List<TransactionResponseDTO> dtoList = new ArrayList<>();

//        List<Transaction> transactionList = transactionRepository.findAllTransactions(startDate, endDate, iban, operator, amount);

        for (int i = 0; i < transactionList.size(); i++) {
            TransactionResponseDTO transactionResponseDTOList = new TransactionResponseDTO();
            transactionResponseDTOList.setId(transactionList.get(i).getId());
            transactionResponseDTOList.setAmount(transactionList.get(i).getAmount());
            transactionResponseDTOList.setBankAccountFrom(transactionList.get(i).getBankAccountFrom());
            transactionResponseDTOList.setBankAccountTo(transactionList.get(i).getBankAccountTo());
            transactionResponseDTOList.setDate(transactionList.get(i).getDate());
            dtoList.add(transactionResponseDTOList);
        }

        return dtoList;
    }
    public List<Transaction> GetTransactionsByIban(String iban) {
        return transactionRepository.findByBankAccountFromOrBankAccountTo(iban, iban);
    }
    public Transaction AddTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction UpdateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction validateTransaction(TransactionRequestDTO transactionRequestDTO) {
        BankAccount bankAccountFrom = bankAccountService.getBankAccountById(transactionRequestDTO.getAccountIdFrom());
        BankAccount bankAccountTo = bankAccountService.getBankAccountById(transactionRequestDTO.getAccountIdTo());
        User performedByUser = transactionRequestDTO.getPerformedByUser();
        if(bankAccountFrom.getIban() == bankAccountTo.getIban()){
            if(bankAccountFrom.getAccountType().get(0) == AccountType.CURRENT && bankAccountTo.getAccountType().get(0) == AccountType.CURRENT || bankAccountFrom.getAccountType().get(0) == AccountType.SAVINGS && bankAccountTo.getAccountType().get(0) == AccountType.SAVINGS){

                throw new ValidateTransactionException("you can't transfer to the same account");
            }
        }
        else {
            if(bankAccountTo.getAccountType().get(0) != AccountType.CURRENT){
                throw new ValidateTransactionException("you can only transfer to a current account");
            }
        }
        if(bankAccountTo.isDisabled() || bankAccountFrom.isDisabled()){
            throw new ValidateTransactionException("you can't transfer to and / or from a disabled account");
        }
        if(performedByUser.getTransactionLimit() < transactionRequestDTO.getAmount()){
            throw new ValidateTransactionException("you can't transfer more than your transaction limit");
        }
        double newbalanceFrom = bankAccountFrom.getBalance() - transactionRequestDTO.getAmount();
        double newbalanceTo = bankAccountTo.getBalance() + transactionRequestDTO.getAmount();
        if(newbalanceFrom < bankAccountFrom.getAbsoluutLimit() || newbalanceFrom < 0){
            throw new ValidateTransactionException("you will end below your absolute limit or below 0");
        }
        bankAccountFrom.setBalance(newbalanceFrom);
        bankAccountTo.setBalance(newbalanceTo);
        double newDailyLimit = performedByUser.getDailyLimit() - transactionRequestDTO.getAmount();
        if(newDailyLimit < 0){

            throw new ValidateTransactionException("you will end below your daily limit");
        }
        performedByUser.setDailyLimit(newDailyLimit);
        return performTransaction(transactionRequestDTO, bankAccountFrom, bankAccountTo, performedByUser);
    }
    private Transaction performTransaction(TransactionRequestDTO transactionRequestDTO, BankAccount bankAccountFrom, BankAccount bankAccountTo, User performedByUser){

//new Transaction(0, "test", 10.20,  "NL12INHO0123456787", "NL12INHO0123456789", List.of(AccountType.CURRENT), List.of(AccountType.CURRENT), "kenmerk", LocalDateTime.now(),null)
//       return AddTransaction(new Transaction(1, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456788", List.of(AccountType.CURRENT), List.of(AccountType.CURRENT), "kenmerk", LocalDateTime.now().minusDays(3),null));
        Transaction transaction = new Transaction(0, transactionRequestDTO.getDescription(), transactionRequestDTO.getAmount(), bankAccountFrom.getIban(), bankAccountTo.getIban(), bankAccountFrom.getAccountType(), bankAccountTo.getAccountType(), transactionRequestDTO.getPaymentReference(), transactionRequestDTO.getDate(), performedByUser);
        Transaction ReturnedTransaction = AddTransaction(transaction);

        if(ReturnedTransaction == null){
            throw new TransactionCreateException("something went wrong while creating the transaction");
        }
        BankAccount newBankAccountFrom = bankAccountService.updateBankAccount(bankAccountFrom, transactionRequestDTO.getAccountIdFrom());
        BankAccount newBankAccountTo = bankAccountService.updateBankAccount(bankAccountTo, transactionRequestDTO.getAccountIdTo());
        if(newBankAccountTo == null || newBankAccountFrom == null){
            throw new BankAccountCreateException("something went wrong while updating the bankaccount");
        }
        EditUserRequestDTO editUserRequestDTO = new EditUserRequestDTO(performedByUser.getId(),performedByUser.getFirstName(), performedByUser.getLastName(), performedByUser.getEmail(), performedByUser.getPhoneNumber(), performedByUser.getStreet(), performedByUser.getCity(), performedByUser.getPostalCode(), performedByUser.getHouseNumber(), performedByUser.isInActive());
        User newUser = userService.updateUser(performedByUser.getId(), editUserRequestDTO);
        if(newUser == null){
            throw new UserCreateException("something went wrong while updating the user");
        }
        return ReturnedTransaction;
    }
}
