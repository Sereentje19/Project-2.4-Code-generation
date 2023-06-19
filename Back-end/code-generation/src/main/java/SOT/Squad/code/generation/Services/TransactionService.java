package SOT.Squad.code.generation.Services;


import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.DTO.TransactionRequestDTO;
import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Repositories.TransactionRepository;
import com.fasterxml.jackson.core.PrettyPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction GetTransactionById(long id) {
        return transactionRepository.findById(id).get();
    }
    public List<Transaction> findByBankAccountAndAccountType(String iban, List<AccountType> accountType) {
        return transactionRepository.findByBankAccountToAndAccountTypeToInOrBankAccountFromAndAccountTypeFromIn(iban, accountType, iban, accountType);
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
                throw new IllegalArgumentException("you can't transfer to the same account");
            }
        }
        else {
            if(bankAccountTo.getAccountType().get(0) != AccountType.CURRENT){
                throw new IllegalArgumentException("you can only transfer to a current account");
            }
        }
        if(bankAccountTo.isDisabled() || bankAccountFrom.isDisabled()){
            throw new IllegalArgumentException("you can't transfer to and / or from a disabled account");
        }
        if(performedByUser.getTransactionLimit() < transactionRequestDTO.getAmount()){
            throw new IllegalArgumentException("you can't transfer more than your transaction limit");
        }
        double newbalanceFrom = bankAccountFrom.getBalance() - transactionRequestDTO.getAmount();
        double newbalanceTo = bankAccountTo.getBalance() + transactionRequestDTO.getAmount();
        if(newbalanceFrom < bankAccountFrom.getAbsoluutLimit() || newbalanceFrom < 0){
            throw new IllegalArgumentException("you will end below your absolute limit or below 0");
        }
        bankAccountFrom.setBalance(newbalanceFrom);
        bankAccountTo.setBalance(newbalanceTo);
        double newDailyLimit = performedByUser.getDailyLimit() - transactionRequestDTO.getAmount();
        if(newDailyLimit < 0){
            throw new IllegalArgumentException("you will end below your daily limit");
        }
        performedByUser.setDailyLimit(newDailyLimit);
        return performTransaction(transactionRequestDTO, bankAccountFrom, bankAccountTo, performedByUser);
    }
    private Transaction performTransaction(TransactionRequestDTO transactionRequestDTO, BankAccount bankAccountFrom, BankAccount bankAccountTo, User performedByUser){
//        return AddTransaction(new Transaction(1, "test", 100,  "NL12INHO0123456789", "NL12INHO0123456788", List.of(AccountType.CURRENT), List.of(AccountType.CURRENT), "kenmerk", LocalDateTime.now().minusDays(3),null));

        Transaction transaction = new Transaction(1, transactionRequestDTO.getDescription(), transactionRequestDTO.getAmount(), bankAccountFrom.getIban(), bankAccountTo.getIban(), bankAccountFrom.getAccountType(), bankAccountTo.getAccountType(), transactionRequestDTO.getPaymentReference(), transactionRequestDTO.getDate(), performedByUser);
        Transaction ReturnedTransaction = AddTransaction(transaction);
        BankAccount newBankAccountFrom = bankAccountService.updateBankAccount(bankAccountFrom, transactionRequestDTO.getAccountIdFrom());
        BankAccount newBankAccountTo = bankAccountService.updateBankAccount(bankAccountTo, transactionRequestDTO.getAccountIdTo());
        User newUser = userService.updateUser(performedByUser);
        return ReturnedTransaction;
    }
}
