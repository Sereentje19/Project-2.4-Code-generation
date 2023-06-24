package SOT.Squad.code.generation.repositories;

import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByBankAccountFromOrBankAccountTo(String bankAccountFrom, String bankAccountTo);

//    @Query("SELECT t FROM Transaction t " +
//            "WHERE (t.bankAccountFrom = :bankAccountFrom OR t.bankAccountTo = :bankAccountTo) ")
//    List<Transaction> findAllTransactions(@Param("bankAccountTo") long bankAccountTo,
//                                          @Param("bankAccountFrom") long bankAccountFrom);
//
//
//    @Query("SELECT t FROM Transaction t " +
//            "WHERE t.date <= :endDate AND t.date >= :startDate " +
//            "AND (:operator is null OR :operator = '' OR (:operator = '<' " +
//            "AND t.amount < :amount) OR (:operator = '>' AND t.amount > :amount) " +
//            "OR (:operator = '=' AND t.amount = :amount)) AND (:iban = '' " +
//            "OR :iban is null OR (t.bankAccountFrom IS NOT null AND t.bankAccountFrom = :iban ) " +
//            "OR (t.bankAccountTo IS NOT null and t.bankAccountTo = :iban))")
//    List<Transaction> findAllTransactions2(Date startDate, Date endDate, String iban, String operator, int amount);


        @Query("SELECT t FROM Transaction t " +
                "WHERE (t.bankAccountFrom = :bankAccountFrom OR t.bankAccountTo = :bankAccountTo) " +
                "AND t.date <= :endDate AND t.date >= :startDate " +
                "AND (:operator is null OR :operator = '' OR (:operator = '<' " +
                "AND t.amount < :amount) OR (:operator = '>' AND t.amount > :amount) " +
                "OR (:operator = '=' AND t.amount = :amount))")
        List<Transaction> findAllTransactions(
                @Param("startDate") LocalDateTime startDate,
                @Param("endDate") LocalDateTime endDate,
                @Param("bankAccountFrom") long bankAccountFrom,
                @Param("bankAccountTo") long bankAccountTo,
                @Param("operator") String operator,
                @Param("amount") int amount);
}
