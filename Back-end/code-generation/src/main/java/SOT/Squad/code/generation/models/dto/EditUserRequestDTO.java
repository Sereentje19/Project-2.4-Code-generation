package SOT.Squad.code.generation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditUserRequestDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
    private String street;
    private String city;
    private String postalCode;
    private int houseNumber;
    private boolean inActive;

}
