package SOT.Squad.code.generation.Cucumber;

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


    @Then("the response should be a bank account object with the same details")
    public void theResponseShouldBeABankAccountObjectWithTheSameDetails() {
        
    }

    @When("I request the bank account with ID {int}")
    public void iRequestTheBankAccountWithID(int arg0) {
        
    }

    @Given("multiple bank accounts in the system")
    public void multipleBankAccountsInTheSystem() {
        
    }

    @When("I request all bank accounts")
    public void iRequestAllBankAccounts() {
        
    }

    @Then("the response should be a list of bank account objects")
    public void theResponseShouldBeAListOfBankAccountObjects() {
        
    }

    @When("I delete the bank account with ID {int}")
    public void iDeleteTheBankAccountWithID(int arg0) {
        
    }

    @Then("the bank account should be deleted successfully")
    public void theBankAccountShouldBeDeletedSuccessfully() {
        
    }

    @When("I update the bank account with ID {int}")
    public void iUpdateTheBankAccountWithID(int arg0) {
        
    }

    @Then("the response should be the updated bank account object")
    public void theResponseShouldBeTheUpdatedBankAccountObject() {
        
    }

    @When("I request all name and IBAN")
    public void iRequestAllNameAndIBAN() {
        
    }

    @Then("the response should be a list of IbanAndNameDTO objects")
    public void theResponseShouldBeAListOfIbanAndNameDTOObjects() {
    }
}
