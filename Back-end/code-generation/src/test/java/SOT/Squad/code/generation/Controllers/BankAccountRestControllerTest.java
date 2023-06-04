package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.Models.AccountType;
import SOT.Squad.code.generation.Models.BankAccount;
import SOT.Squad.code.generation.Models.Currency;
import SOT.Squad.code.generation.Services.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class BankAccountRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private TransactionRestController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();
    }
    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountRestController bankAccountRestController;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();

        // Mock the JWTKeyProvider using @MockBean
        JWTKeyProvider keyProviderMock = Mockito.mock(JWTKeyProvider.class);
        Mockito.when(keyProviderMock.decodeJWT()).thenReturn("mockedToken");

        // Inject the mocked JWTKeyProvider into the controller using @MockBean
        ReflectionTestUtils.setField(transactionController, "keyProvider", keyProviderMock);
    }

    BankAccount bankacc1 = new BankAccount(1, "NL12INHO0123456789",   1000, 1, false, List.of(Currency.EURO), List.of(AccountType.CURRENT));
    BankAccount bankacc2 = new BankAccount(2, "NL12INHO0123456788",  2000, 1, false, List.of(Currency.POUND), List.of(AccountType.SAVINGS));
    BankAccount bankacc3 = new BankAccount(3, "NL12INHO0123456787",  100, 2, false, List.of(Currency.DOLLAR), List.of(AccountType.SAVINGS));

    @Test
    public void deleteBankAccount() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setDisabled(false);

        doNothing().when(bankAccountService).deleteBankAccount(bankAccount);

        mockMvc.perform(MockMvcRequestBuilders.put("/bankaccounts/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"disabled\": false}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        verify(bankAccountService, times(1)).deleteBankAccount(bankAccount);
    }



    @Test
    void getAllBankAccounts() throws Exception {
        List<BankAccount> bankAccounts = new ArrayList<>();
        // Add some transactions to the list

        when(bankAccountService.getAllBankAccounts()).thenReturn(bankAccounts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

}
