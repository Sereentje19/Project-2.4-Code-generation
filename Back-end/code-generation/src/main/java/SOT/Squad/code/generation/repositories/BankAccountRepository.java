package SOT.Squad.code.generation.repositories;

import SOT.Squad.code.generation.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

//    public Iterable<BankAccount> getAllByIban(String iban);

    List<BankAccount> getAllByUserId(long id);

    BankAccount getAllById(long id);


    //create a method to find a bank account by iban
    public BankAccount findFirstByIban(String iban);

}
