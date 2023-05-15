package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    //Transaction findByBankAccount(BankAccount bankAccountFrom)

    public Iterable<Transaction> getAllByBankAccountFrom(String bankAccountFrom);
    public Iterable<Transaction> getAllByBankAccountTo(String bankAccountFrom);


}
