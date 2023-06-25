package SOT.Squad.code.generation.services;


import SOT.Squad.code.generation.exceptions.*;
import SOT.Squad.code.generation.models.AccountType;
import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.models.dto.*;
import SOT.Squad.code.generation.models.Transaction;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.repositories.BankAccountRepository;
import SOT.Squad.code.generation.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    double newbalance = 0;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;

    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> GetAllTransactions() {
        return (List<Transaction>)transactionRepository.findAll();
    }

    public TransactionOverViewDTO GetTransactionById(long id) {
        Transaction t = transactionRepository.findById(id).get();
        TransactionOverViewDTO transactionOverViewDTO = new TransactionOverViewDTO();
        transactionOverViewDTO.setId(t.getId());
        transactionOverViewDTO.setAmount(t.getAmount());
        transactionOverViewDTO.setDate(t.getDate());
        transactionOverViewDTO.setDescription(t.getDescription());
        transactionOverViewDTO.setPaymentReference(t.getPaymentReference());

        BankAccount bankFrom = bankAccountRepository.getAllById(t.getBankAccountFrom());
        BankAccount bankTo = bankAccountRepository.getAllById(t.getBankAccountTo());

        transactionOverViewDTO.setIbanFrom(bankFrom.getIban());
        transactionOverViewDTO.setIbanTo(bankTo.getIban());
        transactionOverViewDTO.setAccountTypeFrom(bankFrom.getAccountType());
        transactionOverViewDTO.setAccountTypeTo(bankTo.getAccountType());
        return transactionOverViewDTO;
    }

    @Autowired
    BankAccountRepository bankAccountRepository;
    public List<TransactionResponseDTO> findBankAccountResponse(long id, Date startDate, Date endDate, String operator, int amount) {
        List<TransactionResponseDTO> dtoList = new ArrayList<>();
        LocalDateTime startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        BankAccount b = bankAccountRepository.findById(id).get();

        List<Transaction> transactionList = transactionRepository.findAllTransactions(
                startDateTime, endDateTime, b.getId(), b.getId(), operator, amount);


        for (int i = 0; i < transactionList.size(); i++) {
            TransactionResponseDTO transactionResponseDTOList = new TransactionResponseDTO();
            transactionResponseDTOList.setId(transactionList.get(i).getId());
            transactionResponseDTOList.setAmount(transactionList.get(i).getAmount());
            transactionResponseDTOList.setBankAccountFrom(transactionList.get(i).getBankAccountFrom());
            transactionResponseDTOList.setBankAccountTo(transactionList.get(i).getBankAccountTo());
            transactionResponseDTOList.setDate(transactionList.get(i).getDate());

            if(transactionList.get(i).getBankAccountFrom() == id) {
                BankAccount account = bankAccountRepository.getAllById(transactionList.get(i).getBankAccountTo());
                transactionResponseDTOList.setIban(account.getIban());
            } else{
                BankAccount account = bankAccountRepository.getAllById(transactionList.get(i).getBankAccountFrom());
                transactionResponseDTOList.setIban(account.getIban());
            }

            dtoList.add(transactionResponseDTOList);
        }

        return dtoList;
    }
    public List<Transaction> GetTransactionsByIban(String iban) {
        return transactionRepository.findByBankAccountFromOrBankAccountTo(iban, iban);
    }
    public Transaction AddTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction UpdateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    public Transaction validateTransaction(TransactionRequestDTO transactionRequestDTO) {
        BankAccount bankAccountFrom = bankAccountService.getBankAccountById(transactionRequestDTO.getAccountIdFrom());
        BankAccount bankAccountTo = bankAccountService.getBankAccountById(transactionRequestDTO.getAccountIdTo());
        User performedByUser = transactionRequestDTO.getPerformedByUser();
        validateTransaciotnAccounTypes(bankAccountFrom, bankAccountTo);

        if(transactionRequestDTO.getPaymentReference() == "") throw new ValidateTransactionException("you need to fill in a payment reference");

        if(bankAccountTo.isDisabled() || bankAccountFrom.isDisabled()) throw new ValidateTransactionException("you can't transfer to and / or from a disabled account");

        if(transactionRequestDTO.getAmount() <= 0) throw new ValidateTransactionException("you can't transfer 0 or less");

        if(performedByUser.getTransactionLimit() < transactionRequestDTO.getAmount()) throw new ValidateTransactionException("you can't transfer more than your transaction limit");

        double newbalanceFrom = bankAccountFrom.getBalance() - transactionRequestDTO.getAmount();
        double newbalanceTo = bankAccountTo.getBalance() + transactionRequestDTO.getAmount();
        if(newbalanceFrom < bankAccountFrom.getAbsoluutLimit() || newbalanceFrom < 0) throw new ValidateTransactionException("you will end below your absolute limit or below 0");
        bankAccountFrom.setBalance(newbalanceFrom);
        bankAccountTo.setBalance(newbalanceTo);

        double newDailyLimit = performedByUser.getDailyLimit() - transactionRequestDTO.getAmount();
        if(newDailyLimit < 0) throw new ValidateTransactionException("you will end below your daily limit");
        performedByUser.setDailyLimit(newDailyLimit);

        return performTransaction(transactionRequestDTO, bankAccountFrom, bankAccountTo, performedByUser);
    }
    private void validateTransaciotnAccounTypes(BankAccount bankAccountFrom, BankAccount bankAccountTo){
        if(bankAccountFrom.getIban() == bankAccountTo.getIban()){
            if(bankAccountFrom.getAccountType().get(0) == AccountType.CURRENT && bankAccountTo.getAccountType().get(0) == AccountType.CURRENT || bankAccountFrom.getAccountType().get(0) == AccountType.SAVINGS && bankAccountTo.getAccountType().get(0) == AccountType.SAVINGS){
                throw new ValidateTransactionException("you can't transfer to the same account");
            }
        }
        else {
            if(bankAccountTo.getAccountType().get(0) != AccountType.CURRENT){
                throw new ValidateTransactionException("you can only transfer to a current account");
            }
        }
    }
    private Transaction performTransaction(TransactionRequestDTO transactionRequestDTO, BankAccount bankAccountFrom, BankAccount bankAccountTo, User performedByUser){
        Transaction ReturnedTransaction = AddTransaction(new Transaction(0, transactionRequestDTO.getDescription(), transactionRequestDTO.getAmount(),   bankAccountFrom.getId(),  bankAccountTo.getId(), transactionRequestDTO.getPaymentReference(), transactionRequestDTO.getDate(),null));

        if(ReturnedTransaction == null){
            throw new TransactionCreateException("something went wrong while creating the transaction");
        }
        BankAccount newBankAccountFrom = bankAccountService.updateBankAccount(bankAccountFrom, transactionRequestDTO.getAccountIdFrom());
        BankAccount newBankAccountTo = bankAccountService.updateBankAccount(bankAccountTo, transactionRequestDTO.getAccountIdTo());
        if(newBankAccountTo == null || newBankAccountFrom == null){
            throw new BankAccountUpdateException("something went wrong while updating the bankaccounts");
        }
        EditUserRequestDTO editUserRequestDTO = new EditUserRequestDTO(performedByUser.getId(),performedByUser.getFirstName(), performedByUser.getLastName(), performedByUser.getEmail(), performedByUser.getPhoneNumber(), performedByUser.getStreet(), performedByUser.getCity(), performedByUser.getPostalCode(), performedByUser.getHouseNumber(), performedByUser.isInActive());
        User newUser = userService.updateUser(performedByUser.getId(), editUserRequestDTO);
        if(newUser == null){
            throw new UserUpdateException("something went wrong while updating the user");
        }
        return ReturnedTransaction;
    }

    public BankAccount validateWithdrawOrDeposit(withdrawOrDepositDTO withdrawOrDeposit) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(withdrawOrDeposit.getBankAccountId());
//        return bankAccount;
        User performedByUser = userService.getUserObject(withdrawOrDeposit.getPerformedByUser().getId());
        if(bankAccount.isDisabled()) {
            throw new ValidateWithdrawOrTransactionException("you can't withdraw or deposit from a disabled account");
        }
        if(withdrawOrDeposit.getBedrag() <= 0) {
            throw new ValidateWithdrawOrTransactionException("you can't withdraw or deposit 0 or less");
        }


        if(withdrawOrDeposit.getChoise().toLowerCase().toString().equals("withdraw")){
//            throw new ValidateWithdrawOrTransactionException(withdrawOrDeposit.getChoise() + " is not a valid choise");

            this.newbalance = bankAccount.getBalance() - withdrawOrDeposit.getBedrag();
            if(this.newbalance < bankAccount.getAbsoluutLimit() || this.newbalance < 0) {
                throw new ValidateWithdrawOrTransactionException("you will end below your absolute limit or below 0");
            }
            if(performedByUser.getTransactionLimit() < withdrawOrDeposit.getBedrag()) {
                throw new ValidateWithdrawOrTransactionException("you can't withdraw or deposit more than your transaction limit|" + performedByUser.getTransactionLimit() +"|" + withdrawOrDeposit.getBedrag());
            }
            double newDailyLimit = performedByUser.getDailyLimit() - withdrawOrDeposit.getBedrag();
            if(newDailyLimit < 0){
                throw new ValidateTransactionException("you will end below your daily limit");
            }
            performedByUser.setDailyLimit(newDailyLimit);
        }

        if(withdrawOrDeposit.getChoise().toLowerCase().toString().equals("deposit")){
            this.newbalance = bankAccount.getBalance() + withdrawOrDeposit.getBedrag();
//            throw new ValidateWithdrawOrTransactionException(withdrawOrDeposit.getChoise() + " is not a valid choise");

        }
//        throw new ValidateWithdrawOrTransactionException(withdrawOrDeposit.getChoise().toLowerCase().toString());
        bankAccount.setBalance(this.newbalance);

        BankAccount newBankAccount = bankAccountService.updateBankAccount(bankAccount, withdrawOrDeposit.getBankAccountId());
        if(newBankAccount == null){
            throw new BankAccountUpdateException("something went wrong while updating the bankaccount");
        }
        EditUserRequestDTO editUserRequestDTO = new EditUserRequestDTO(performedByUser.getId(),performedByUser.getFirstName(), performedByUser.getLastName(), performedByUser.getEmail(), performedByUser.getPhoneNumber(), performedByUser.getStreet(), performedByUser.getCity(), performedByUser.getPostalCode(), performedByUser.getHouseNumber(), performedByUser.isInActive());
        User newUser = userService.updateUser(performedByUser.getId(), editUserRequestDTO);
        if(newUser == null){
            throw new UserUpdateException("something went wrong while updating the user");
        }
        return newBankAccount;
    }



//    private Object performWithdrawOrDeposit(withdrawOrDepositDTO withdrawOrDeposit, BankAccount bankAccount, User performedByUser) {
//
//        Transaction ReturnedTransaction = AddTransaction(new Transaction(0, withdrawOrDeposit.getOmschrijving(), withdrawOrDeposit.getBedrag(),   withdrawOrDeposit.getBankAccountId(),  bankAccountTo.getId(), transactionRequestDTO.getPaymentReference(), transactionRequestDTO.getDate(),null));
//
//        if(ReturnedTransaction == null){
//            throw new TransactionCreateException("something went wrong while creating the transaction");
//        }
//        BankAccount newBankAccountFrom = bankAccountService.updateBankAccount(bankAccountFrom, transactionRequestDTO.getAccountIdFrom());
//        BankAccount newBankAccountTo = bankAccountService.updateBankAccount(bankAccountTo, transactionRequestDTO.getAccountIdTo());
//        if(newBankAccountTo == null || newBankAccountFrom == null){
//            throw new BankAccountUpdateException("something went wrong while updating the bankaccount");
//        }
//        User newUser = userService.updateUser(performedByUser.getId(), editUserRequestDTO);
//        if(newUser == null){
//            throw new UserUpdateException("something went wrong while updating the user");
//        }
//        return ReturnedTransaction;
//    }
}
