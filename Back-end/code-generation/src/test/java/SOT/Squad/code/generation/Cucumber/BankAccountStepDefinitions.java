package SOT.Squad.code.generation.Cucumber;

import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class BankAccountStepDefinitions{

    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    private String jwtToken;

    private ResponseEntity<String> response;


    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Given("the user is logged in as {string} with password {string}")
    public void theUserIsLoggedInAsWithPassword(String arg0, String arg1) {
    }

//    @Given("The endpoint for {string} is available for method {string}")
//    public void theEndpointForIsAvailableForMethod(String arg0, String arg1) {
//
//    }

    @When("I retreive all bank accounts")
    public void iRetreiveAllBankAccounts() {
    }

    @Then("the response should be a list of bank account objects")
    public void theResponseShouldBeAListOfBankAccountObjects() {
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAsWithPassword(String arg0, String arg1) {
    }

    @When("a new bank account is added")
    public void aNewBankAccountIsAdded() {
    }

    @Then("the response should be a bank account object")
    public void theResponseShouldBeABankAccountObject() {
    }

    @When("I request the bank account with ID {int}")
    public void iRequestTheBankAccountWithID(int arg0) {
    }

    @Then("the response should be a bank account object with the ID {int}")
    public void theResponseShouldBeABankAccountObjectWithTheID(int arg0) {
    }

    @Given("I am logged in as an employee with the credentials username {string} and password {string}")
    public void iAmLoggedInAsAnEmployeeWithTheCredentialsUsernameAndPassword(String arg0, String arg1) {
    }

    @When("I delete the bank account with ID {int}")
    public void iDeleteTheBankAccountWithID(int arg0) {
    }

    @Then("the bank account should be put to disabled successfully")
    public void theBankAccountShouldBePutToDisabledSuccessfully() {
    }

    @When("I update the bank account with ID {int}")
    public void iUpdateTheBankAccountWithID(int arg0) {
    }

    @Then("the response should be the updated bank account object with ID {int}")
    public void theResponseShouldBeTheUpdatedBankAccountObjectWithID(int arg0) {
    }

    @When("I request the ID, iban, name and accountType of all bank accounts")
    public void iRequestTheIDIbanNameAndAccountTypeOfAllBankAccounts() {
    }

    @Then("the response should be a list of bank account objects with only the ID, iban, name and accountType")
    public void theResponseShouldBeAListOfBankAccountObjectsWithOnlyTheIDIbanNameAndAccountType() {
    }
}
