package SOT.Squad.code.generation.generators;

import SOT.Squad.code.generation.models.BankAccount;
import SOT.Squad.code.generation.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class IbanGenerator {

    private String generatedIban;

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public IbanGenerator(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void generateIban() {
        do {
            String countryCode = "NL";
            String additionalDigits = String.format("%02d", new Random().nextInt(100));
            String bankCode = "INHO";
            String accountNumber = String.format("%08d", new Random().nextLong() % 100000000L);

            generatedIban = countryCode + additionalDigits + bankCode + additionalDigits + accountNumber.substring(0, 8);

        } while (checkIbanExists());
    }

    public String getGeneratedIban() {
        generateIban();
        return generatedIban;
    }

    private boolean checkIbanExists() {
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();

        for (BankAccount bankAccount : bankAccountList) {
            if (bankAccount.getIban() != null && bankAccount.getIban().equals(generatedIban)) {
                return true;
            }
        }
        return false;
    }


}
