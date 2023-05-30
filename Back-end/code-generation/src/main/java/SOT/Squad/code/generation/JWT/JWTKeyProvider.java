package SOT.Squad.code.generation.JWT;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Key;
import java.security.KeyStore;

@Component
public class JWTKeyProvider {

    @Value("${jwt.key-store}")
    private String keystore;
    @Value("${jwt.key-store-password}")
    private String password;
    @Value("${jwt.key-alias}")
    private String alias;
    private Key privateKey;

    @PostConstruct
    protected void init() {
        try {
            Resource resource = new ClassPathResource(keystore);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(resource.getInputStream(), password.toCharArray());
            privateKey = keyStore.getKey(alias, password.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public String decodeJWT(@RequestHeader("Authorization") String authorizationHeader) {

        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return null;
            }

            String jwtToken = authorizationHeader.replace("Bearer ", "");
            String username = Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJws(jwtToken).getBody().getSubject();
            return username;

        } catch (JwtException e) {
            return null;
        }
    }
}
