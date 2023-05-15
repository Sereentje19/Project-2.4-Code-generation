package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    public Iterable<BankAccount> getAllByIban(String iban);
    public Iterable<BankAccount> getAllByUserId(long userId);
    public BankAccount getByIbanAndAccountType(String iban, String accountType);

//    public Iterable<BankAccount> getOneByIbanAndAccountType(String iban, String accountType);
}
