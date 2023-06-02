package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //Transaction findByBankAccount(BankAccount bankAccountFrom);
//    @Query(value = "SELECT * FROM transactions WHERE BANK_ACCOUNT_FROM = ?1 OR BANK_ACCOUNT_TO = ?1", nativeQuery = true)
//    List<Transaction> getByIban(String iban);

    List<Transaction> findByBankAccountFromOrBankAccountTo(String bankAccountFrom, String bankAccountTo);

}
