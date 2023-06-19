package SOT.Squad.code.generation.Models.DTO;

import SOT.Squad.code.generation.Models.AccountType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;
import java.util.List;

@Data
public class BankAccountInfoDTO {
    private long id;
    private String iban;
    private String currencies;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<AccountType> accountType;
}
