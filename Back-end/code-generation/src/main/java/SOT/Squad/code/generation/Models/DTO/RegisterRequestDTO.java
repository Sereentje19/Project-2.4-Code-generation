package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequestDTO {
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String pincode;
}
