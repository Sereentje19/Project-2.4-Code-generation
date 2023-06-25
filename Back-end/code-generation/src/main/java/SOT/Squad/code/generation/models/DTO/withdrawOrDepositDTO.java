package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class withdrawOrDepositDTO {
    @Id
    @GeneratedValue
    private long id;
    private int bankAccountId;
    private int bedrag;
    private String omschrijving;
    private String choise;
    @OneToOne
    private User performedByUser;
}
