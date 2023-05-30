package SOT.Squad.code.generation.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
