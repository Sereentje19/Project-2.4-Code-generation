package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.AccountType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.List;

@Data
public class BankDropDownDTO {
    private long id;
    private String Name;
    private String Iban;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<AccountType> accountType;
}
