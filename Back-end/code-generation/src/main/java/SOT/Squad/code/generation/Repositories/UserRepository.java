package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//    @Query(value = "SELECT * FROM Users LIMIT ?1 OFFSET ?2", nativeQuery = true)
//    public Iterable<User> getAllUsersWithLimitAndOffset(int limit, int offset);

    public User getByUsernameAndPassword(String Username, String Password);

}
