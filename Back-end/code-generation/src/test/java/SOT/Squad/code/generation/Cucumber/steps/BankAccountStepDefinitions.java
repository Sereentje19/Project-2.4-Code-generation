package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;


public class BankAccountStepDefinitions {
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private final HttpHeaders httpHeaders = new HttpHeaders();

    private String jwtToken;

    private ResponseEntity<String> responseEntity;

    private final ObjectMapper mapper = new ObjectMapper();
    private List<BankAccount> retreivedBankaccount;

    private BankAccountRepository bankAccountRepository;
    private String uri = "http://localhost:8080//";


    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAsWithPassword(String arg0, String arg1) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange(uri + "login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + arg0 + "\", \"password\":\"" + arg1 + "\"}", httpHeaders), // null because OPTIONS does not have a body
                        String.class);

        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }

    @And("The endpoint for {string} is available for method {string}")
    public void theEndpointForIsAvailableForMethod(String endpoint, String method) {
        responseEntity = restTemplate
                .exchange(uri + endpoint,
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

    @When("I retrieve all bank accounts")
    public void iRetrieveAllBankAccounts() {
        ResponseEntity<List<BankAccount>> responseEntity = restTemplate.exchange(
                uri + "bankaccounts",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {}
        );

        List<BankAccount> bankAccounts = responseEntity.getBody();
        retreivedBankaccount = bankAccounts;
    }


    @Then("the response should be a list of bank account objects")
    public void theResponseShouldBeAListOfBankAccountObjects() {
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(retreivedBankaccount);
        Assertions.assertFalse(retreivedBankaccount.isEmpty());
    }

    @When("a new bank account is added")
    public void aNewBankAccountIsAdded() {
        BankAccount account = new BankAccount();
        account.setId(2L);
        account.setUserId(2L);
        account.setAccountType(List.of(AccountType.CURRENT));
        account.setCurrencies("EUR");
        account.setDisabled(false);
        account.setAbsoluutLimit(1000);
        account.setIban("NL12INHO0123456787");
        account.setBalance(2000);

        responseEntity = restTemplate.exchange(uri + "bankaccounts",
                HttpMethod.POST,
                new HttpEntity<>(account, httpHeaders),
                String.class);
    }

    @Then("the response should be a bank account object")
    public void theResponseShouldBeABankAccountObject() {
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @When("I request the bank account with ID {int}")
    public void iRequestTheBankAccountWithID(int id) {
        responseEntity = restTemplate.exchange(
                uri + "bankaccounts/" + id,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Then("the response should be a bank account object with the ID {int}")
    public void theResponseShouldBeABankAccountObjectWithTheID(int id) {
        Assertions.assertNotNull(responseEntity, "Response entity is null.");
        Assertions.assertNotNull(responseEntity.getBody(), "Response body is null.");

        int actual = JsonPath.read(responseEntity.getBody(), "$.id");
        Assertions.assertEquals(id, actual);
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
