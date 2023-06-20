package SOT.Squad.code.generation.Models.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private long id;
    private double amount;
    private String bankAccountFrom;
    private String bankAccountTo;
    private LocalDateTime date;
}
