package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.models.AccountType;

import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.BankAccountInfoDTO;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
import SOT.Squad.code.generation.repositories.BankAccountRepository;
import SOT.Squad.code.generation.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBankAccounts_shouldReturnAllBankAccounts() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(new BankAccount());
        bankAccounts.add(new BankAccount());

        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        List<BankAccount> result = bankAccountService.getAllBankAccounts();

        assertEquals(2, result.size());
    }

    @Test
    void testAddBankAccount_withValidBankAccount_shouldReturnSavedBankAccount() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setUserId(2L);

        User user = new User();
        user.setBankAccountList(List.of(bankAccount.getId()));

        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        // Act
        BankAccount result = bankAccountService.addBankAccount(bankAccount);

        // Assert
        assertEquals(bankAccount, result);
        assertEquals(2, result.getUserId());
        // Add additional assertions as needed
    }

    @Test
    void testAddBankAccount_withMissingUser_shouldThrowException() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);

        // Act and Assert
        assertThrows(BankAccountCreateException.class, () -> bankAccountService.addBankAccount(bankAccount));
        verify(bankAccountRepository, never()).save(any());
        verify(userRepository, never()).findById(anyLong());
    }


    @Test
    void updateBankAccount_shouldUpdateBankAccount() {
        BankAccount bankAccount = new BankAccount();
        long id = 1L;

        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        BankAccount result = bankAccountService.updateBankAccount(bankAccount, id);

        assertEquals(bankAccount, result);
        assertEquals(id, bankAccount.getId());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void deleteBankAccount_shouldDisableBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccountService.deleteBankAccount(bankAccount);

        assertTrue(bankAccount.isDisabled());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void getBankAccountById_shouldReturnBankAccount() {
        long id = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        when(bankAccountRepository.getAllById(id)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.getBankAccountById(id);
        assertEquals(bankAccount, result);
    }


    @Test
    void testAddIbanToBankAccount_withEmptyBankAccountListAndNullIban_shouldGenerateIban() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        User user = new User();

        when(userService.getUser(any())).thenReturn(user);
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        BankAccount result = bankAccountService.addIbanToBankAccount(bankAccount, user);

        // Assert
        assertNotNull(result.getIban());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }


    @Test
    void testAddIbanToBankAccount_withNonEmptyBankAccountListAndNullIban_shouldGetIbanFromUser() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        User user = new User();
        List<Long> bankAccountList = new ArrayList<>();
        bankAccountList.add(1L);
        user.setBankAccountList(bankAccountList);

        BankAccount optionalAccount = new BankAccount();
        optionalAccount.setIban("IBAN123");

        when(userService.getUser(Mockito.any(Long.class))).thenReturn(user);
        when(bankAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(optionalAccount));

        // Act
        BankAccount result = bankAccountService.addIbanToBankAccount(bankAccount, user);

        // Assert
        assertEquals(optionalAccount.getIban(), result.getIban());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }


    @Test
    void testAddIbanToBankAccount_withNonEmptyBankAccountListAndNonNullIban_shouldNotChangeIban() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("IBAN123");
        User user = new User();
        List<Long> bankAccountList = new ArrayList<>();
        bankAccountList.add(1L);
        user.setBankAccountList(bankAccountList);

        when(userService.getUser(any())).thenReturn(user);

        // Act
        BankAccount result = bankAccountService.addIbanToBankAccount(bankAccount, user);

        // Assert
        assertEquals("IBAN123", result.getIban());
        verify(bankAccountRepository, times(0)).save(bankAccount);
    }

    @Test
    void testAddAccountListToBankAccount_withNonExistingBankAccountId_shouldAddBankAccountIdToList() {
        // Arrange
        BankAccount savedBankAccount = new BankAccount();
        savedBankAccount.setId(1L);
        User user = new User();

        List<Long> bankAccountList = new ArrayList<>();
        user.setBankAccountList(bankAccountList);

        when(userService.getUser(anyLong())).thenReturn(user);  // Use anyLong() instead of any()
        when(userRepository.save(any())).thenReturn(user);

        // Act
        BankAccount result = bankAccountService.addAccountListToBankAccount(savedBankAccount, user);

        // Assert
        assertTrue(user.getBankAccountList().contains(savedBankAccount.getId()));
        verify(userRepository, times(1)).save(user);
        assertEquals(savedBankAccount, result);
    }


    @Test
    void testAddAccountListToBankAccount_withExistingBankAccountId_shouldNotAddBankAccountIdToList() {
        // Arrange
        BankAccount savedBankAccount = new BankAccount();
        savedBankAccount.setId(1L);
        User user = new User();

        List<Long> bankAccountList = new ArrayList<>();
        bankAccountList.add(1L);
        user.setBankAccountList(bankAccountList);

        when(userService.getUser(anyLong())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        BankAccount result = bankAccountService.addAccountListToBankAccount(savedBankAccount, user);

        // Assert
        assertEquals(bankAccountList, user.getBankAccountList());
        verify(userRepository, times(1)).save(user);
        assertEquals(savedBankAccount, result);
    }




    @Test
    void testGetAllNameAndIbanFirst_withNonEmptyBankList_shouldReturnPopulatedDTOList() {
        // Arrange
        List<BankAccount> bankList = new ArrayList<>();
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        bankAccount1.setIban("IBAN1");
        bankAccount1.setUserId(1L);
        bankList.add(bankAccount1);

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        when(userService.getUser(1L)).thenReturn(user1);

        // Act
        List<BankDropDownDTO> result = bankAccountService.getAllNameAndIbanFirst(bankList);

        // Assert
        assertEquals(1, result.size());
        BankDropDownDTO dto = result.get(0);
        assertEquals("IBAN1", dto.getIban());
        assertEquals("John Doe", dto.getName());
        assertEquals(bankAccount1.getAccountType(), dto.getAccountType());
        assertEquals(bankAccount1.getId(), dto.getId());
    }

    @Test
    void testGetAllNameAndIban_withNonEmptyBankList_shouldReturnPopulatedDTOList() {
        // Arrange
        List<BankAccount> bankList = new ArrayList<>();
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        bankAccount1.setIban("IBAN1");
        bankAccount1.setUserId(1L);
        bankList.add(bankAccount1);

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        when(userService.getUser(1L)).thenReturn(user1);

        // Act
        List<BankDropDownDTO> result = bankAccountService.getAllNameAndIban(bankList);

        // Assert
        assertEquals(1, result.size());
        BankDropDownDTO dto = result.get(0);
        assertEquals("IBAN1", dto.getIban());
        assertEquals("John Doe", dto.getName());
        assertEquals(bankAccount1.getAccountType(), dto.getAccountType());
        assertEquals(bankAccount1.getId(), dto.getId());
    }

    @Test
    void testGetBankAccountInfo_withValidId_shouldReturnBankAccountInfoDTO() {
        // Arrange
        long id = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setIban("IBAN1");
        bankAccount.setAccountType(List.of(AccountType.SAVINGS));
        bankAccount.setCurrencies("USD");

        when(bankAccountRepository.getAllById(id)).thenReturn(bankAccount);

        // Act
        BankAccountInfoDTO result = bankAccountService.getBankAccountInfo(id);

        // Assert
        assertEquals(id, result.getId());
        assertEquals("IBAN1", result.getIban());
        assertEquals(List.of(AccountType.SAVINGS), result.getAccountType());
        assertEquals("USD", result.getCurrencies());
    }

    @Test
    void testGetAccountTypes_withExistingBankAccounts_shouldReturnAvailableAccountTypes() {
        // Arrange
        long userId = 1L;
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountType(List.of(AccountType.CURRENT));
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountType(List.of(AccountType.SAVINGS));
        BankAccount bankAccount3 = new BankAccount();
        bankAccount3.setAccountType(List.of(AccountType.CURRENT));
        BankAccount bankAccount4 = new BankAccount();
        bankAccount4.setAccountType(List.of(AccountType.SAVINGS));
        BankAccount bankAccount5 = new BankAccount();
        bankAccount5.setAccountType(List.of(AccountType.SAVINGS));

        List<BankAccount> bankAccountList = Arrays.asList(bankAccount1, bankAccount2, bankAccount3, bankAccount4, bankAccount5);

        when(bankAccountRepository.getAllByUserId(userId)).thenReturn(bankAccountList);

        // Act
        List<AccountType> result = bankAccountService.getAccountTypes(userId);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testGetBankAccountByIban_withValidIban_shouldReturnBankAccount() {
        // Arrange
        String iban = "IBAN123";
        BankAccount bankAccount = new BankAccount();

        when(bankAccountRepository.findFirstByIban(iban)).thenReturn(bankAccount);

        // Act
        BankAccount result = bankAccountService.getBankAccountByIban(iban);

        // Assert
        assertEquals(bankAccount, result);
    }


}
