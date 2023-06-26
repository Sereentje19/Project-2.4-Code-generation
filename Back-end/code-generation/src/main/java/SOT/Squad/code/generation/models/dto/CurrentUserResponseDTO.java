package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String city;
    private String postalCode;
    private int houseNumber;
    private boolean inActive;
    private List<Long> bankAccountList;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

}
