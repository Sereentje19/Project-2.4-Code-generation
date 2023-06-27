package SOT.Squad.code.generation.repositories;

import SOT.Squad.code.generation.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import SOT.Squad.code.generation.models.BankAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

     User findUserByPincode(String pincode);
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.pincode LIKE %:keyword%")
    List<User> searchUsers(String keyword);

}
