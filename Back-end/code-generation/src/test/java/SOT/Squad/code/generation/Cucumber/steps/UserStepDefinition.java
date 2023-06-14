package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class UserStepDefinition{

    BankAccount bankAccount = new BankAccount();
    User user = new User();
    Transaction transaction = new Transaction();

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String jwtToken;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    private ResponseEntity<String> responseEntity;

    @Given("The user is logged in with username {string} and the password {string}")
    public void theUserIsLoggedInWithUsernameAndThePassword(String arg0, String arg1) throws JsonProcessingException {

        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("http://localhost:8080/login",
                HttpMethod.POST,
                new HttpEntity<>(
                        mapper.writeValueAsString(Map.of("username", arg0, "password", arg1)),
                        httpHeaders
                ), String.class);
        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }

 @Given("The endpoint for {string} is available for method {string}")
 public void theEndpointForIsAvailableForMethod(String endpoint,String method) throws Throwable {
     responseEntity = restTemplate
             .exchange("http://localhost:8080/" + "users",
                     HttpMethod.OPTIONS,
                     new HttpEntity<>(null, httpHeaders), // null because OPTIONS does not have a body
                     String.class);

     List<String> options = Arrays.stream(responseEntity.getHeaders()
                     .get("Allow")
                     .get(0)// The first element is all allowed methods separated by comma
                     .split(","))
             .toList();
     Assertions.assertTrue(options.contains(method));
 }
    @When("I retrieve all users")
    public void iRetrieveAllUsers() {
        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("http://localhost:8080/users",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class);

        int statusCode = responseEntity.getStatusCodeValue();
        // Add your validation logic here
        Assert.assertEquals(200, statusCode);
    }

    @Then("I should receive all users")
    public void iShouldReceiveAllUsers() {
        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("http://localhost:8080/users",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class);

        // Add your validation logic here
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Given("the system has a database with users")
    public void theSystemHasADatabaseWithUsers() {
    }

    @When("I request to get a single user with an id of {string}")
    public void iRequestToGetASingleUser(String id) {
        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("http://localhost:8080/users/" + id,
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class);

        int statusCode = responseEntity.getStatusCodeValue();
        // Add your validation logic here
        Assert.assertEquals(200, statusCode);
    }

    @Then("I should receive a single user with an id of {string}")
    public void iShouldReceiveASingleUser(String id) {
        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("http://localhost:8080/users/" + id,
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class);
        User user = JsonPath.read(responseEntity.getBody(), "$.token");
        Assert.assertEquals(user.getId(), id);
    }

    @When("I request to get a single user that does not exist")
    public void iRequestToGetASingleUserThatDoesNotExist() {
    }

    @Then("I should receive a error")
    public void iShouldReceiveAError() {
    }

    @When("I request to create a new user with a valid token")
    public void iRequestToCreateANewUserWithAValidToken() {
    }

    @Then("I should receive a new user")
    public void iShouldReceiveANewUser() {
    }

    @When("I request to create a new user with invalid token")
    public void iRequestToCreateANewUserWithInvalidToken() {
    }

    @When("I request to create a new user without a token")
    public void iRequestToCreateANewUserWithoutAToken() {
    }

    @When("I request to update a user with a valid token")
    public void iRequestToUpdateAUserWithAValidToken() {
    }

    @Then("I should receive a updated user")
    public void iShouldReceiveAUpdatedUser() {
    }

    @When("I request to update a user with invalid token")
    public void iRequestToUpdateAUserWithInvalidToken() {
    }

    @When("I request to update a user without a token")
    public void iRequestToUpdateAUserWithoutAToken() {
    }



    @Then("I should receive a deleted user")
    public void iShouldReceiveADeletedUser() {
    }

    @When("I request to delete a user with invalid token")
    public void iRequestToDeleteAUserWithInvalidToken() {
    }

    @When("I request to delete a user without a token")
    public void iRequestToDeleteAUserWithoutAToken() {
    }

    @Then("The user should be disabled")
    public void theUserShouldBeDisabled() {
    }

    @When("I request to delete a user with an id of {string}")
    public void iRequestToDeleteAUserWithAnIdOf(String arg0) {
        httpHeaders.add("Content-Type", "application/json ");

        User newUser = new User(1, "thijs", "moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",null,true, List.of(Role.CUSTOMER), "5781",2000,300);

        ResponseEntity<User> responseEntity = restTemplate.exchange("http://localhost:8080/users/" + arg0,
                HttpMethod.PUT,
                new HttpEntity<>(newUser, httpHeaders),
                User.class);
//
        User responseUser = responseEntity.getBody();
        Assert.assertEquals(responseUser.getId(), arg0);
    }
}
