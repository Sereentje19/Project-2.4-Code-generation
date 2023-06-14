package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.Transaction;
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
    public void iAmLoggedInAsWithPassword(String username, String password) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange(uri + "login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}", httpHeaders), // null because OPTIONS does not have a body
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
        account.setId(2);
        account.setUserId(2);
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
    public void iAmLoggedInAsAnEmployeeWithTheCredentialsUsernameAndPassword(String username, String password) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange(uri + "login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}", httpHeaders), // null because OPTIONS does not have a body
                        String.class);

        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }

    @When("The bank account with ID {int} is put to disabled")
    public void theBankAccountWithIDIsPutToDisabled(int id) {
//        BankAccount bankAccount = new BankAccount(id, "NL12INHO0123456787",  100, 3, false, "EUR", List.of(AccountType.CURRENT),10);
        BankAccount bankAccount = new BankAccount(2, "NL12INHO0123456789", 2000, 1, false, "EUR", List.of(AccountType.CURRENT), 10);

        httpHeaders.add("Content-Type", "application/json");

//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setId(id);
//        bankAccount.setDisabled(true);
//        bankAccount.setUserId(2);
//        bankAccount.setAccountType(List.of(AccountType.CURRENT));
//        bankAccount.setCurrencies("EUR");
//        bankAccount.setAbsoluutLimit(1000);
//        bankAccount.setIban("NL12INHO0123456787");
//        bankAccount.setBalance(2000);

        responseEntity = restTemplate.exchange(
                uri + "bankaccounts/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(bankAccount, httpHeaders),
                String.class);

        // Extract the response body as a string
        String responseBody = responseEntity.getBody();

        // Print the response body to check its content
        System.out.println(responseEntity.getBody());
    }

    @Then("the bank account with ID {int} should be put to disabled successfully")
    public void theBankAccountWithIDShouldBePutToDisabledSuccessfully(int id) {
        String responseBody = responseEntity.getBody();

        // Check if the response body is not null or empty
        Assertions.assertNotNull(responseBody, "Response body is null");
        Assertions.assertTrue(!responseBody.isEmpty(), "Response body is empty");

        String actual = JsonPath.read(responseBody, "$.id");
        Assertions.assertEquals(id, actual);
    }

    @When("I update the bank account with ID {int}")
    public void iUpdateTheBankAccountWithID(int arg0) {
    }

    @Then("the response should be the updated bank account object with ID {int}")
    public void theResponseShouldBeTheUpdatedBankAccountObjectWithID(int arg0) {
    }

    @When("I request the ID, iban, name and accountType of all bank accounts")
    public void iRequestTheIDIbanNameAndAccountTypeOfAllBankAccounts() {
//        ResponseEntity<List<BankAccount>> responseEntity = restTemplate.exchange(
//                uri + "bankaccounts",
//                HttpMethod.GET,
//                new HttpEntity<>(httpHeaders),
//                new ParameterizedTypeReference<>() {}
//        );
//
//        List<BankAccount> bankAccounts = responseEntity.getBody();
//        retreivedBankaccount = bankAccounts;
    }

    @Then("the response should be a list of bank account objects with only the ID, iban, name and accountType")
    public void theResponseShouldBeAListOfBankAccountObjectsWithOnlyTheIDIbanNameAndAccountType() {
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

//        // Retrieve the bank account objects from the response body
//        String bankAccounts = responseEntity.getBody();
//
//        // Validate the list of bank accounts
//        Assertions.assertNotNull(bankAccounts);
//        Assertions.assertFalse(bankAccounts.isEmpty());
//
//        // Check that each bank account object contains only the required attributes
//        for (BankAccount account : bankAccounts) {
//            Assertions.assertNotNull(account.getId());
//            Assertions.assertNotNull(account.getIban());
////            Assertions.assertNotNull(account.getName());
//            Assertions.assertNotNull(account.getAccountType());
//            Assertions.assertNull(account.getBalance());
//            // Add assertions for any other attributes that should not be present
//            // ...
//        }
    }
}
