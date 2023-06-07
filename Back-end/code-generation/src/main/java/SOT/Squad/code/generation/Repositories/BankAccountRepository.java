package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    //    public Iterable<BankAccount> getAllByUserId(long userId);
    public Iterable<BankAccount> getAllByIban(String iban);
    public BankAccount getAllById(long id);
//        @Modifying
//        @Query( value = "UPDATE BankAccount e SET e.disabled = true WHERE e.id = :id", nativeQuery = true)
//        void setDisabledTrueById(@Param("id") long id);
//        void setDisabledTrueById(long id);



//    @Modifying
//    @Query("UPDATE BankAccount b SET b.disabled = true WHERE b.id = :id")

//    what is a good JpaRepository method for this?


}
