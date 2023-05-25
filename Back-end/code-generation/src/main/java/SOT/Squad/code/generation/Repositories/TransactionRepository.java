package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    //Transaction findByBankAccount(BankAccount bankAccountFrom);
    @Query(value = "SELECT * FROM transactions WHERE iban = ?1", nativeQuery = true)
    List<Transaction> getByIban(String iban);
}
