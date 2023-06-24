package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.AccountType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionOverViewDTO {
    private long id;
    private double amount;
    private long bankAccountFrom;
    private long bankAccountTo;
    private LocalDateTime date;
    private String ibanFrom;
    private String ibanTo;
    private String description;
    private String paymentReference;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<AccountType> accountTypeFrom;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<AccountType> accountTypeTo;
}
