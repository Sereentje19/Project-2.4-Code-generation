package SOT.Squad.code.generation.models;

import org.springframework.security.core.GrantedAuthority;

public enum AccountType implements GrantedAuthority {
    CURRENT,
    SAVINGS;
    @Override
    public String getAuthority() {
        return null;
    }
}

