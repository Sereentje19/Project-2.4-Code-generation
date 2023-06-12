package SOT.Squad.code.generation.Cucumber;

import SOT.Squad.code.generation.Controllers.UserRestController;
import SOT.Squad.code.generation.Models.User;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import io.cucumber.java8.En;
@RunWith(Cucumber.class)
public class CrudUsersSteps implements En{

    @Autowired
    private UserRestController userRestController;

    private List<User> retrievedUsers;

    @Given("the system has a database with users")
    public void setupDatabase() {
        // Implement the setup logic to prepare the database
        databaseService.addUser(new User("John Doe", "john@example.com"));
        databaseService.addUser(new User("Jane Smith", "jane@example.com"));
    }
}
