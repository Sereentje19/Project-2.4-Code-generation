package SOT.Squad.code.generation.Cucumber.steps;

import SOT.Squad.code.generation.Repositories.BankAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public class BankAccountStepDefinitions {

//    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    private String jwtToken;

    private ResponseEntity<String> responseEntity;

    private final ObjectMapper mapper = new ObjectMapper();

//    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Given("the user is logged in as {string} with password {string}")
    public void theUserIsLoggedInAsWithPassword(String arg0, String arg1) {
        httpHeaders.add("Content-Type", "application/json");
        responseEntity = restTemplate
                .exchange("/login",
                        HttpMethod.POST,
                        new HttpEntity<>("{\"username\":\"" + arg0 + "\", \"password\":\"" + arg1 + "\"}", httpHeaders), // null because OPTIONS does not have a body
                        String.class);

        jwtToken = JsonPath.read(responseEntity.getBody(), "$.token");
        httpHeaders.add("Authorization", "Bearer " + jwtToken);
    }
    
//
//    @Given("I am logged in as username {string} with password {string}")
//    public void iAmLoggedInAsUsernameWithPassword(String arg0, String arg1) throws Throwable {
//        httpHeaders.add("Content-Type", "application/json");
//        response = restTemplate
//                .exchange("/" + "login",
//                        HttpMethod.POST,
//                        new HttpEntity<>("{\"username\":\"" + arg0 + "\", \"password\":\"" + arg1 + "\"}", httpHeaders), // null because OPTIONS does not have a body
//                        String.class);
//
//        token = JsonPath.read(response.getBody(), "$.token");
//        httpHeaders.add("Authorization", "Bearer " + token);
//    }
//
//    @Given("The endpoint for transactions is available for method {string}")
//    public void theEndpointForIsAvailableForMethod(String method) throws Throwable {
//        response = restTemplate
//                .exchange("/" + "transactions",
//                        HttpMethod.OPTIONS,
//                        new HttpEntity<>(null, httpHeaders), // null because OPTIONS does not have a body
//                        String.class);
//
//        List<String> options = Arrays.stream(response.getHeaders()
//                        .get("Allow")
//                        .get(0)// The first element is all allowed methods separated by comma
//                        .split(","))
//                .toList();
//        Assertions.assertTrue(options.contains(method));
//    }
//
//    @When("the transaction is added")
//    public void theTransactionIsAdded() {
//        bankAccount = new BankAccount(1, "NL12INHO0123456789", 1000, 1, false, "EUR", List.of(AccountType.CURRENT),10);
//        user = new User(1, "thijs", "moerland", "Thijs", "Moerland", 64567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",
//                List.of(bankAccount.getId()), true, List.of(Role.CUSTOMER), "5781", 2000, 300);
//        Transaction transaction = new Transaction(1, "test", 100, "NL12INHO0123456789", "NL12INHO0123456788",
//                List.of(AccountType.CURRENT), List.of(AccountType.CURRENT), "kenmerk",
//                LocalDateTime.now().minusDays(3), user);
//
//        response = restTemplate.exchange("/transactions",
//                HttpMethod.POST,
//                new HttpEntity<>(transaction, httpHeaders),
//                String.class);
//    }
//
//    @Then("the transaction is successfully created")
//    public void theTransactionIsSuccessfullyCreated() {
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }


    @And("The endpoint for {string} is available for method {string}")
    public void theEndpointForIsAvailableForMethod(String arg0, String arg1) {

    }

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
