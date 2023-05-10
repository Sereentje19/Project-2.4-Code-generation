package SOT.Squad.code.generation.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Transactions\"")
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private double amount;
    private String type;
    private String bankAccountFrom;
    private String bankAccountTo;
}
