package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.List;

@Data
public class CurrentUserResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private List<Long> bankAccountList;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
