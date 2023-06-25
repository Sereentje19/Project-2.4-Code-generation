package SOT.Squad.code.generation.repositories;

import SOT.Squad.code.generation.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> getAllByUserId(long id);
    BankAccount getAllById(long id);
    BankAccount findFirstByIban(String iban);

}
