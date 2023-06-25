package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.models.*;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
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
import java.util.Collections;
import java.util.List;


public class BankAccountStepDefinitions {
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String jwtToken;
    private ResponseEntity<String> responseEntity;
    List<BankDropDownDTO> tetreivedBankAccounts;
    List<AccountType> retreivedAccountType;
    private List<BankAccount> retreivedBankaccount;
    private String uri = "http://localhost:8080/";


    @Given("I am logged in as user {string} with password {string}")
    public void iAmLoggedInUserAsWithPassword(String username, String password) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange(uri + "login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}", httpHeaders), // null because OPTIONS does not have a body
                        String.class);

        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }

    @And("The endpoint for {string} is available for the method {string}")
    public void theEndpointForIsAvailableForTheMethod(String endpoint, String method) {
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

    @When("The bank account with ID {int} is put to disabled")
    public void theBankAccountWithIDIsPutToDisabled(int id) {
        httpHeaders.add("Content-Type", "application/json");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setDisabled(true);

        responseEntity = restTemplate.exchange(
                uri + "bankaccounts",
                HttpMethod.PUT,
                new HttpEntity<>(bankAccount, httpHeaders),
                String.class);
    }

    @Then("the bank account should be put to disabled successfully")
    public void theBankAccountShouldBePutToDisabledSuccessfully() {
        String responseBody = responseEntity.getBody();

        // Check if the response body is not null or empty
        Assertions.assertNotNull(responseBody, "Response body is null");
        Assertions.assertTrue(!responseBody.isEmpty(), "Response body is empty");

        // Check if the response body is a boolean indicating success
        boolean success = Boolean.parseBoolean(responseBody);
        Assertions.assertTrue(success, "Bank account was not put to disabled successfully");
    }

    @When("I update the bank account with ID {int}")
    public void iUpdateTheBankAccountWithID(int id) {
        httpHeaders.add("Content-Type", "application/json");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setDisabled(true);
        bankAccount.setUserId(2);
        bankAccount.setAccountType(List.of(AccountType.CURRENT));
        bankAccount.setCurrencies("EUR");
        bankAccount.setAbsoluutLimit(1000);
        bankAccount.setIban("NL12INHO0123456787");
        bankAccount.setBalance(2000);

        responseEntity = restTemplate.exchange(
                uri + "bankaccounts/change/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(bankAccount, httpHeaders),
                String.class);
    }

    @Then("the response should be the updated bank account object with ID {int}")
    public void theResponseShouldBeTheUpdatedBankAccountObjectWithID(int id) {
        String responseBody = responseEntity.getBody();

        // Check if the response body is not null or empty
        Assertions.assertNotNull(responseBody, "Response body is null");
        Assertions.assertTrue(!responseBody.isEmpty(), "Response body is empty");

        Integer actual = JsonPath.read(responseBody, "$.id");
        Assertions.assertEquals(id, actual.intValue());
    }


    @When("I request the ID, iban, name and accountType of all bank accounts")
    public void iRequestTheIDIbanNameAndAccountTypeOfAllBankAccounts() {
        ResponseEntity<List<BankDropDownDTO>> responseEntity = restTemplate.exchange(
                uri + "/bankaccounts/All",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {}
        );

        List<BankDropDownDTO> bankAccounts = responseEntity.getBody();
        tetreivedBankAccounts = bankAccounts;
    }

    @Then("the response should be a list of bank account objects with only the ID, iban, name and accountType")
    public void theResponseShouldBeAListOfBankAccountObjectsWithOnlyTheIDIbanNameAndAccountType() {
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assert.assertNotNull(tetreivedBankAccounts);

        for (BankDropDownDTO bankAccount : tetreivedBankAccounts) {
            Assert.assertNotNull(bankAccount.getId());
            Assert.assertNotNull(bankAccount.getIban());
            Assert.assertNotNull(bankAccount.getName());
            Assert.assertNotNull(bankAccount.getAccountType());
        }
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        // No specific implementation needed as this is a precondition
    }

    @Then("the response should be an unauthorized error")
    public void theResponseShouldBeAnUnauthorizedError() {
        // Verify that the response status code is 401 Unauthorized
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }


    @When("I request the bank account with IBAN {string}")
    public void iRequestTheBankAccountWithIBAN(String iban) {
        ResponseEntity<List<BankAccount>> responseEntity = restTemplate.exchange(
                uri + "bankaccounts/iban/{iban}",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {},
                iban
        );

        List<BankAccount> bankAccounts = responseEntity.getBody();
        retreivedBankaccount = bankAccounts;

    }

    @Then("the response should be a bank account object with the specified IBAN")
    public void theResponseShouldBeABankAccountObjectWithTheSpecifiedIBAN() {
        // Assert that the response is a bank account object with the specified IBAN
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String iban = "NL12INHO0123456789";

        // Perform assertions on the bank account object
        Assert.assertNotNull(retreivedBankaccount);
        Assert.assertEquals(iban, retreivedBankaccount.get(0).getIban());
        // Additional assertions for other properties as needed
    }

    @When("I request all bank accounts for the user with ID {int}")
    public void iRequestAllBankAccountsForTheUserWithID(int id) {
        ResponseEntity<List<BankAccount>> responseEntity = restTemplate.exchange(
                uri + "/bankaccounts/userID/{id}",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {},
                id
        );

        List<BankAccount> bankAccounts = responseEntity.getBody();
        retreivedBankaccount = bankAccounts;
    }


    @When("I request the account types for the user with ID {int}")
    public void iRequestTheAccountTypesForTheUserWithID(int id) {
        ResponseEntity<List<AccountType>> responseEntity = restTemplate.exchange(
                uri + "/bankaccounts/accountType/{userId}",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {},
                id
        );

        List<AccountType> accountType = responseEntity.getBody();
        retreivedAccountType = accountType;
    }

    @Then("the response should be a list of account types")
    public void theResponseShouldBeAListOfAccountTypes() {
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(retreivedAccountType);
    }

}
