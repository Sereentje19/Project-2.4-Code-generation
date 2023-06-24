package SOT.Squad.code.generation.repositories;

import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByBankAccountFromOrBankAccountTo(String bankAccountFrom, String bankAccountTo);
//    List<Transaction> findByBankAccountToAndAccountTypeToInOrBankAccountFromAndAccountTypeFromIn(
//            String bankAccountTo, List<AccountType> accountTypeTo, String bankAccountFrom, List<AccountType> accountTypeFrom);

//    @Query("SELECT t FROM Transaction t " +
//            "WHERE t.bankAccountFrom = :bankAccountFrom " +
//            "AND t.accountTypeFrom = :bankAccountFrom " +
//            "OR t.bankAccountTo = :bankAccountTo " +
//            "AND t.accountTypeTo = :accountTypeTo ")
//    List<Transaction> findAllTransactions(String bankAccountTo, List<AccountType> accountTypeTo, String bankAccountFrom, List<AccountType> accountTypeFrom, Date startDate, Date endDate, String iban, String operator, int amount);

//    @Query("SELECT t FROM Transaction t " +
//            "WHERE (t.bankAccountFrom = :bankAccountFrom AND :accountTypeFrom = t.accountTypeFrom) " +
//            "OR (t.bankAccountTo = :bankAccountTo AND :accountTypeTo = t.accountTypeTo)")
//    List<Transaction> findAllTransactions(@Param("bankAccountTo") String bankAccountTo,
//                                          @Param("accountTypeTo") AccountType accountTypeTo,
//                                          @Param("bankAccountFrom") String bankAccountFrom,
//                                          @Param("accountTypeFrom") AccountType accountTypeFrom);


//    @Query("SELECT t FROM Transaction t " +
//            "WHERE :bankAccountFrom = t.bankAccountFrom " +
//            "OR :bankAccountTo = t.bankAccountTo")
//    List<Transaction> findAllTransactions(@Param("bankAccountTo") long bankAccountTo,
//                                          @Param("bankAccountFrom") long bankAccountFrom);




    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.bankAccountFrom = :bankAccountFrom OR t.bankAccountTo = :bankAccountTo) ")
    List<Transaction> findAllTransactions(@Param("bankAccountTo") long bankAccountTo,
                                          @Param("bankAccountFrom") long bankAccountFrom);


//    @Query("SELECT t FROM Transaction t " +
//            "WHERE t.date <= :endDate AND t.date >= :startDate " +
//            "AND (:operator is null OR :operator = '' OR (:operator = '<' " +
//            "AND t.amount < :amount) OR (:operator = '>' AND t.amount > :amount) " +
//            "OR (:operator = '=' AND t.amount = :amount)) AND (:iban = '' " +
//            "OR :iban is null OR (t.bankAccountFrom IS NOT null AND t.bankAccountFrom = :iban ) " +
//            "OR (t.bankAccountTo IS NOT null and t.bankAccountTo = :iban))")
//    List<Transaction> findAllTransactions(Date startDate, Date endDate, String iban, String operator, int amount);

}
