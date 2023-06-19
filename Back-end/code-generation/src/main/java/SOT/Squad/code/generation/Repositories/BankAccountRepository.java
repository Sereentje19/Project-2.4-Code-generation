package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.DTO.BankAccountInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    public Iterable<BankAccount> getAllByIban(String iban);

    List<BankAccount> getAllByUserId(long id);

    BankAccount getAllById(long id);


    //create a method to find a bank account by iban
    public BankAccount findFirstByIban(String iban);

//    @Query("SELECT NEW SOT.Squad.code.generation.Models.DTO.BankAccountInfoDTO(b.id, b.iban, b.currencies, b.accountType) FROM BankAccount b WHERE b.id = :id")
//    List<BankAccountInfoDTO> findBankAccountInfo(@Param("id") Long id);

//    @Query("SELECT b.id, b.iban, b.currencies, b.accountType FROM BankAccount b WHERE b.id = :id")
//    BankAccountInfoDTO findBankAccountInfo(@Param("id") Long id);

    public BankAccountInfoDTO findBankAccountById(long id);


}
