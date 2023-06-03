package SOT.Squad.code.generation.Models;

import org.springframework.security.core.GrantedAuthority;

public enum Currency implements GrantedAuthority  {
    EURO,
    DOLLAR,
    POUND;
    @Override
    public String getAuthority() {
        return null;
    }
}

