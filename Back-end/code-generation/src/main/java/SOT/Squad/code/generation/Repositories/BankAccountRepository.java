package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    public Iterable<BankAccount> getAllByIban(String iban);

    public Iterable<BankAccount> getAllByUserId(long userId);
    @Query(value = "UPDATE BankAccount Set disabled = true WHERE iban = ?1", nativeQuery = true)
    public void deleteByIban(String iban);
}
