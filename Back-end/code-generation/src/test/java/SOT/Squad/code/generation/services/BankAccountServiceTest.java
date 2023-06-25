package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.BankAccountCreateException;
import SOT.Squad.code.generation.generators.IbanGenerator;
import SOT.Squad.code.generation.models.AccountType;

import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.BankAccountInfoDTO;
import SOT.Squad.code.generation.models.dto.BankDropDownDTO;
import SOT.Squad.code.generation.models.dto.CurrentUserResponseDTO;
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


import java.util.*;

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
        bankAccount.setId(2);
        bankAccount.setUserId(2L);

        User user = new User();
        user.setBankAccountList(List.of(bankAccount.getId()));

        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(bankAccountRepository.findById(any())).thenReturn(Optional.of(bankAccount));

        // Act
        BankAccount result = bankAccountService.addBankAccount(bankAccount);

        // Assert
        assertEquals(bankAccount, result);
        assertEquals(2L, result.getUserId());
    }




    @Test
    void testAddBankAccount_withMissingUser_shouldThrowException() {
        BankAccount bankAccount = new BankAccount(2, "NL12INHO0123456789", 1000, 0, false, "EUR", List.of(AccountType.CURRENT), 10);
        BankAccount savedBankAccount = new BankAccount(3, "NL12INHO0123456789", 1000, 0, false, "EUR", List.of(AccountType.CURRENT), 10);

        when(bankAccountRepository.findById(2L)).thenReturn(Optional.empty());
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(savedBankAccount);

        assertThrows(BankAccountCreateException.class, () -> bankAccountService.addBankAccount(bankAccount));

        verify(bankAccountRepository).save(bankAccount); // Verify that save method is invoked
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
    public void testAddIbanToBankAccount_withEmptyBankAccountListAndNullIban_shouldGenerateIban() {
        // Mock user
        User user = new User();
        user.setBankAccountList(new ArrayList<>());

        // Mock bank account
        BankAccount bankAccount = new BankAccount();

        // Mock the behavior of the bankAccountRepository and ibanGenerator
        when(bankAccountRepository.findById(0L)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.findAll()).thenReturn(Collections.singletonList(bankAccount));

        // Invoke the method under test
        BankAccount result = bankAccountService.addIbanToBankAccount(bankAccount, user);

        // Verify the generated iban is set
        assertTrue(result.getIban().matches("NL\\d{2}INHO\\d{2}\\d{8}"));
    }



    @Test
    void testAddIbanToBankAccount_withNonEmptyBankAccountListAndNonNullIban_shouldNotChangeIban() {
        // Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("existingIban");

        User user = new User();
        user.setBankAccountList(Collections.singletonList(123L));

        BankAccount existingAccount = new BankAccount();
        existingAccount.setIban("existingIban");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(bankAccountRepository.findById(any())).thenReturn(Optional.of(existingAccount));

        // Act
        BankAccount result = bankAccountService.addIbanToBankAccount(bankAccount, user);

        // Assert
        assertEquals("existingIban", result.getIban());
        // Add additional assertions as needed
    }



    @Test
    void testAddAccountListToBankAccount_withNonExistingBankAccountId_shouldAddBankAccountIdToList() {
        // Arrange
        BankAccount savedBankAccount = new BankAccount();
        savedBankAccount.setId(1L);
        User user = new User();

        List<Long> bankAccountList = new ArrayList<>();
        user.setBankAccountList(bankAccountList);

        when(userService.getUserObject(anyLong())).thenReturn(user);  // Use anyLong() instead of any()
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

        when(userService.getUserObject(anyLong())).thenReturn(user);
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

        CurrentUserResponseDTO user1 = new CurrentUserResponseDTO();
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
        bankAccount1.setAccountType(List.of(AccountType.CURRENT));
        bankAccount1.setUserId(1L);
        bankList.add(bankAccount1);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(2L);
        bankAccount2.setIban("IBAN2");
        bankAccount2.setAccountType(List.of(AccountType.CURRENT));
        bankAccount2.setUserId(2L);
        bankList.add(bankAccount2);

        CurrentUserResponseDTO user1 = new CurrentUserResponseDTO();
        user1.setId(2L);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        when(userService.getUser(2L)).thenReturn(user1);

        List<BankDropDownDTO> result = bankAccountService.getAllNameAndIban(bankList);

        assertEquals(1, result.size());
        BankDropDownDTO dto = result.get(0);
        assertEquals("IBAN2", dto.getIban());
        assertEquals("John Doe", dto.getName());
        assertEquals(bankAccount2.getAccountType(), dto.getAccountType());
        assertEquals(bankAccount2.getId(), dto.getId());
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
        assertEquals("USD", result.getCurrencies());
    }

    @Test
    void testGetAccountTypes_withExistingBankAccounts_shouldReturnAvailableAccountTypes() {
        // Arrange
        long userId = 123;
        List<BankAccount> bankAccountList = new ArrayList<>();

        // Create bank accounts with different account types
        BankAccount account1 = new BankAccount();
        account1.setAccountType(Collections.singletonList(AccountType.CURRENT));
        bankAccountList.add(account1);

        BankAccount account2 = new BankAccount();
        account2.setAccountType(Collections.singletonList(AccountType.SAVINGS));
        bankAccountList.add(account2);

        BankAccount account3 = new BankAccount();
        account3.setAccountType(Arrays.asList(AccountType.CURRENT, AccountType.SAVINGS));
        bankAccountList.add(account3);

        when(bankAccountRepository.getAllByUserId(userId)).thenReturn(bankAccountList);

        // Act
        List<AccountType> result = bankAccountService.getAccountTypes(userId);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(AccountType.SAVINGS));
        // Add additional assertions as needed
    }


    @Test
    void testGetBankAccountByIban_withValidIban_shouldReturnBankAccount() {
        String iban = "NL12INHO0123456789";
        BankAccount bankAccount = new BankAccount();

        when(bankAccountRepository.findFirstByIban(iban)).thenReturn(bankAccount);
        BankAccount result = bankAccountService.getBankAccountByIban(iban);

        assertEquals(bankAccount, result);
    }


}
