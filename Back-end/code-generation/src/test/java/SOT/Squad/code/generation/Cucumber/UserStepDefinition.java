package SOT.Squad.code.generation.Cucumber;

import ch.qos.logback.core.BasicStatusManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Qualifier("baseStepDefinitions")
public class UserStepDefinition extends baseStepDefinitions{

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtToken;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    private ResponseEntity<String> responseEntity;
    @Given("The user is logged in with username {string} and the password {string}")
    public void theUserIsLoggedInWithUsernameAndThePassword(String arg0, String arg1) throws JsonProcessingException {

        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(
                        mapper.writeValueAsString(Map.of("username", arg0, "password", arg1)),
                        httpHeaders
                ), String.class);
        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }

    @Given("The endpoint for {string} is available with method {string}")
    public void theEndpointForIsAvailableWithMethod(String arg0, String arg1) throws JsonProcessingException {
        httpHeaders.add("Content-Type", "application/json ");
        responseEntity = restTemplate.exchange("/users",
                HttpMethod.GET,
                null,
                String.class);

        int statusCode = responseEntity.getStatusCodeValue();
        // Add your validation logic here
        Assert.assertEquals(200, statusCode);
    }

    @When("I retrieve all users")
    public void iRetrieveAllUsers() {
    }

    @Then("I should receive all users")
    public void iShouldReceiveAllUsers() {
    }

    @Given("the system has a database with users")
    public void theSystemHasADatabaseWithUsers() {
    }

    @When("I request to get a single user")
    public void iRequestToGetASingleUser() {
    }

    @Then("I should receive a single user")
    public void iShouldReceiveASingleUser() {
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

    @When("I request to delete a user with a valid token")
    public void iRequestToDeleteAUserWithAValidToken() {
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
}
