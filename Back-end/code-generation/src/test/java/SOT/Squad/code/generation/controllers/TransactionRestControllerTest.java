package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.BankAccountUpdateException;
import SOT.Squad.code.generation.exceptions.TransactionCreateException;
import SOT.Squad.code.generation.exceptions.UserUpdateException;
import SOT.Squad.code.generation.exceptions.ValidateTransactionException;
import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.Role;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.CurrentUserResponseDTO;
import SOT.Squad.code.generation.models.dto.TransactionRequestDTO;
import SOT.Squad.code.generation.models.dto.withdrawOrDepositDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.Transaction;
import SOT.Squad.code.generation.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

class TransactionRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionRestController transactionController;
    private JWTKeyProvider keyProvider;

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
    void addTransaction() throws TransactionCreateException, BankAccountUpdateException, UserUpdateException, ValidateTransactionException {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        Transaction transaction = new Transaction();
        when(transactionService.validateTransaction(requestDTO)).thenReturn(transaction);

        // Act
        ResponseEntity<?> response = transactionController.addTransaction(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody()); // Update the assertion to match the Transaction object
        verify(transactionService, times(1)).validateTransaction(requestDTO);
    }

    @Test
    void withdrawOrDeposit() throws  Exception{
        withdrawOrDepositDTO withdrawOrDeposit = new withdrawOrDepositDTO();
        withdrawOrDeposit.setBankAccountId(1);
        withdrawOrDeposit.setBedrag(100);
        withdrawOrDeposit.setChoise("withdraw");
        User performedByUser = new User();
        performedByUser.setId(1);
        withdrawOrDeposit.setPerformedByUser(performedByUser);

        // Perform the test
        ResponseEntity<?> response = transactionController.withdrawOrDeposit(withdrawOrDeposit);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllTransactions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTransactionById() throws Exception{
        long transactionId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByBankAccountAndAccountType() throws Exception{
        String bankAccount = "NL12INHO0123456789"; // Replace with the actual bank account number you want to test
        String accountType = "savings"; // Replace with the actual account type you want to test

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .param("bankAccount", bankAccount)
                        .param("accountType", accountType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTransaction() throws Exception{
        long transactionId = 1; // Replace with the actual transaction ID you want to update

        String updatedTransactionJson = "{\n" +
                "    \"amount\": 500.00,\n" +
                "    \"description\": \"Updated transaction\",\n" +
                "    \"bankAccount\": \"1234567890\",\n" +
                "    \"accountType\": \"savings\"\n" +
                "}"; // Replace with the updated transaction JSON you want to send

        mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTransactionJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}