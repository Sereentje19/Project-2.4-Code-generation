package SOT.Squad.code.generation.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"BankAccounts\"")
public class BankAccount {
    @Id
    @GeneratedValue
    private long id;
    private String iban;
    private String accountType;
    private String currency;
    private double balance;
    private long userId;
    private boolean disabled;

}
