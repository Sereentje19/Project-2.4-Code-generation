package SOT.Squad.code.generation.Models.DTO;

import SOT.Squad.code.generation.Models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private double amount;
    private int accountIdFrom;
    private int accountIdTo;
    private String paymentReference;
    private LocalDateTime date;
    @OneToOne
    private User performedByUser;

}
