package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
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

    BankAccount getAllById(long id);
    BankAccount findByIban(String iban);

    List<BankAccount> getAllByUserId(long id);
}
