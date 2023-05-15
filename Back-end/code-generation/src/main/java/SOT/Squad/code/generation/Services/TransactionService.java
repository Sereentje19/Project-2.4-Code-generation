package SOT.Squad.code.generation.Services;


import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountService bankAccountService;

    private List<Transaction> transactions = new ArrayList<>();
    public List<Transaction> GetAllTransactions() {
        return (List<Transaction>)transactionRepository.findAll();
    }
    public Transaction GetTransaction(long id) {
        return transactionRepository.findById(id).get();
    }
    public Transaction AddTransaction(Transaction transaction) {
        BankAccount bankAccountFrom = bankAccountService.getBankAccountByIbanAndType(transaction.getBankAccountFrom(), transaction.getAccountFromtype());
        BankAccount bankAccountTo = bankAccountService.getBankAccountByIbanAndType(transaction.getBankAccountTo(), transaction.getAccountTotype());



        return transactionRepository.save(transaction);
    }


    //public Transaction GetTransactionByAccount(BankAccount bankAccountFrom) {
    //    return transactionRepositrory.findByBankAccount(bankAccountFrom);
    //}
    //public List<Transaction> GetTransactionsByUser(BankAccount bankAccountFrom) {
    //    return (List<Transaction>)transactionRepositrory.findById(bankAccountFrom);
    //}
    //public List<Transaction> addTransaction(Transaction transaction) {
    //    retur transactionRepositrory.save(transaction);
    //}
}
