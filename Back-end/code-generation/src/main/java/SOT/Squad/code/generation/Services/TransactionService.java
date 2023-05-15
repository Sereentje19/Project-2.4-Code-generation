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
    public Transaction GetTransactionById(long id) {
        return transactionRepository.findById(id).get();
    }
    public List<Transaction> GetAllByIban(String string) {
        List<Transaction> fromList = (List<Transaction>) transactionRepository.getAllByBankAccountFrom(string);
        List<Transaction> toList = (List<Transaction>) transactionRepository.getAllByBankAccountTo(string);

        //write a function that returns a list of transactions and removes the duplicates
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : fromList) {
            if (!transactions.contains(transaction)) {
                transactions.add(transaction);
            }
        }
        for (Transaction transaction : toList) {
            if (!transactions.contains(transaction)) {
                transactions.add(transaction);
            }
        }
        return transactions;
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
