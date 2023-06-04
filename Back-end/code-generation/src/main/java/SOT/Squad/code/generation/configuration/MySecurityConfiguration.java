package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.JWT.JWTTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration {

    @Autowired
    JWTTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // We need to do this to allow POST requests

        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeHttpRequests()
                //login
                .requestMatchers("/login").permitAll()
                .requestMatchers("/users/current").authenticated()

                //users
                .requestMatchers("/users/pincode/{pincode}").authenticated()
                .requestMatchers("/users").authenticated()

                //transactions
                .requestMatchers("/transactions/account/{iban}/{type}").authenticated()
                .requestMatchers("/transactions").authenticated()
                .requestMatchers("/transactions/{id}").authenticated()

                //bankaccounts
                .requestMatchers("/bankaccounts/{id}").authenticated()
                .requestMatchers("/bankaccounts").authenticated();

        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }


}

