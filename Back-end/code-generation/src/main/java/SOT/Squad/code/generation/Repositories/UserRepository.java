package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User getByUsernameAndPassword(String Username, String Password);
    Optional<User> findUserByUsername(String username);
    public User getUserByUsername(String username);
}
