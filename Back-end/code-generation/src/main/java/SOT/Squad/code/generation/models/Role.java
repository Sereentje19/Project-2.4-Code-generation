package SOT.Squad.code.generation.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    CUSTOMER,
    EMPLOYEE, USER;

    @Override
    public String getAuthority() {
        return null;
    }
}
