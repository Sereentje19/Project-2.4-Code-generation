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
            Random random = new Random();
            String countryCode = "NL";
            long additionalDigits = 10L + ((long) (random.nextDouble() * (99L - 10L)));
            String bankCode = "INHO";
            long accountNumber = 10000000L + ((long) (random.nextDouble() * (99999999L - 10000000L)));

            generatedIban = countryCode + additionalDigits + bankCode + additionalDigits + accountNumber;
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
