package SOT.Squad.code.generation.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Users\"")
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String street;
    private int houseNumber;
    private String postalCode;
    private String city;
//    @OneToMany(mappedBy = "user")
//    private List<BankAccount> bankAccountList;
    private List<Long> bankAccountList;

}
