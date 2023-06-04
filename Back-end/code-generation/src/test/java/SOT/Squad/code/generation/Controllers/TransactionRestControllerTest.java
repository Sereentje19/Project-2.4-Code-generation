package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.Transaction;
import SOT.Squad.code.generation.Services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
class TransactionRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionRestController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();

        // Mock the JWTKeyProvider using @MockBean
        JWTKeyProvider keyProviderMock = Mockito.mock(JWTKeyProvider.class);
        Mockito.when(keyProviderMock.decodeJWT()).thenReturn("mockedToken");

        // Inject the mocked JWTKeyProvider into the controller using @MockBean
        ReflectionTestUtils.setField(transactionController, "keyProvider", keyProviderMock);

    }
    @Test
    void addTransaction() throws Exception {
        Transaction transaction = new Transaction();
        // Set up your transaction object

        when(transactionService.AddTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        // Add some transactions to the list

        when(transactionService.GetAllTransactions()).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getTransactionsByIban() throws Exception {
        String iban = "NL12INHO0123456789";
        List<Transaction> transactions = new ArrayList<>();
        // Add some transactions to the list

        when(transactionService.GetTransactionsByIban(iban)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{iban}", iban)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getTransactionById() throws Exception {
        // Arrange
        long transactionId = 1;
        Transaction transaction = new Transaction(1, "test", 100.0, "NL12INHO0123456789", "NL12INHO0123456787",
                List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk");
        when(transactionService.GetTransactionById(transactionId)).thenReturn(transaction);

        // Act and Assert
        mockMvc.perform(get("/transactions/info/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.description").value(transaction.getDescription()))
                .andExpect(jsonPath("$.amount").value(transaction.getAmount()))
                .andExpect(jsonPath("$.bankAccountFrom").value(transaction.getBankAccountFrom()))
                .andExpect(jsonPath("$.fromIban").doesNotExist()); // Assert that "fromIban" field does not exist
    }

    @Test
    void findByBankAccountAndAccountType() throws Exception {
        String iban = "NL12INHO0123456789";
        List<AccountType> accountTypes = List.of(AccountType.CURRENT, AccountType.SAVINGS);
        List<Transaction> transactions = new ArrayList<>();

        // Add some transactions to the list that match the provided IBAN and account types
        transactions.add(new Transaction(1, "test", 100.0, "NL12INHO0123456789", "NL12INHO0123456787",
                List.of(AccountType.CURRENT), List.of(AccountType.SAVINGS), "kenmerk"));
        // Mock the service method call
        when(transactionService.findByBankAccountAndAccountType(iban, accountTypes)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/account/{iban}/{type}", iban, accountTypes.stream().map(AccountType::toString).collect(Collectors.joining(",")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void updateTransaction() throws Exception {
        long transactionId = 1L;
        Transaction updatedTransaction = new Transaction();

        when(transactionService.UpdateTransaction(any(Transaction.class))).thenReturn(updatedTransaction);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTransaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }
    @Test
    void deleteTransaction() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}