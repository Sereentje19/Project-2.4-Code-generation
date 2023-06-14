package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class LoginStepDefinitions {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String jwtToken;
    private ResponseEntity<String> responseEntity;
    private final ObjectMapper mapper = new ObjectMapper();

    private String uri = "http://localhost:8080/";

    @Given("I have valid login credentials username {string} and password {string}")
    public void iHaveValidLoginCredentialsUsernameAndPassword(String username, String password) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange(uri + "login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}", httpHeaders), // null because OPTIONS does not have a body
                        String.class);

        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }


    @When("I submit my login credentials")
    public void iSubmitMyLoginCredentials() {
    }

    @Then("the response should be a successful login response with a token")
    public void theResponseShouldBeASuccessfulLoginResponseWithAToken() {
    }

    @Given("I have invalid login credentials username {string} and password {string}")
    public void iHaveInvalidLoginCredentialsUsernameAndPassword(String username, String password) {
    }

    @Then("the response should be an error indicating invalid credentials")
    public void theResponseShouldBeAnErrorIndicatingInvalidCredentials() {
    }

    @Given("I have missing login credentials")
    public void iHaveMissingLoginCredentials() {
    }

    @Then("the response should be an error indicating missing credentials")
    public void theResponseShouldBeAnErrorIndicatingMissingCredentials() {
    }

}
