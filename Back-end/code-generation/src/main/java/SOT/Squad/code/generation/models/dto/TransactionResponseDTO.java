package SOT.Squad.code.generation.models.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private long id;
    private double amount;
    private long bankAccountFrom;
    private long bankAccountTo;
    private LocalDateTime date;
    private String iban;
}
