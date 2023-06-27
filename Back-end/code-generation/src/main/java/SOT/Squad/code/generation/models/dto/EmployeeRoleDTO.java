package SOT.Squad.code.generation.models.dto;

import SOT.Squad.code.generation.models.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeRoleDTO {
    private long id;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> employeeRole;
}
