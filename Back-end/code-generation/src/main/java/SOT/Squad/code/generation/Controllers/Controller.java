package SOT.Squad.code.generation.Controllers;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Controller {

        private static final String SECRET_KEY = "SECRET_KEY";

        public Claims checkForJwt() throws Exception {
            // Check for token header
            String authHeader = getAuthorizationHeader();
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new Exception("No token provided");
            }

            // Extract the JWT from the header
            String jwt = authHeader.substring(7); // Remove "Bearer " prefix

            // Decode JWT
            try {
                return Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
            } catch (JwtException e) {
                throw new Exception("Invalid token");
            }
        }

        private String getAuthorizationHeader() {
            // Implement the logic to retrieve the Authorization header value from the request
            // This may vary depending on the framework or library you are using
            // Here's an example using Java Servlet API:
//            HttpServletRequest request = ...; // Get the HttpServletRequest object
//            return request.getHeader("Authorization");
            return "";
        }






}
