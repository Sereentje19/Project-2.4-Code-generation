package SOT.Squad.code.generation.models;

import SOT.Squad.code.generation.exceptions.UserCreateException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private long phoneNumber;
    private String email;
    private String street;
    private int houseNumber;
    private String postalCode;
    private String city;

    private List<Long> bankAccountList;
    private boolean inActive;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String pincode;
    private double dailyLimit;
    private int transactionLimit;




}