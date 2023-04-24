package SOT.Squad.code.generation.Repositories;

import SOT.Squad.code.generation.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
