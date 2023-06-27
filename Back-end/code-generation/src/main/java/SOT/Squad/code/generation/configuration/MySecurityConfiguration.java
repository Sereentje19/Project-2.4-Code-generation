package SOT.Squad.code.generation.configuration;

import SOT.Squad.code.generation.jwt.JWTTokenFilter;
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

                //users
                .requestMatchers("/users/register").permitAll()
                .requestMatchers("/users").authenticated()
                .requestMatchers("/users/role/{id}").authenticated()
                .requestMatchers("/users/{id}").authenticated()
                .requestMatchers("/users/role").authenticated()
                .requestMatchers("/users/current").authenticated()
                .requestMatchers("/users/dropdown").authenticated()
                .requestMatchers("/users/pincode/{pincode}").authenticated()

                //transactions
                .requestMatchers("/transactions").authenticated()
                .requestMatchers("/transactions/{id}").authenticated()
                .requestMatchers("/transactions/account/{id}").authenticated()
                .requestMatchers("/transactions/withdrawOrDeposit").authenticated()

                //bankaccounts
                .requestMatchers("/bankaccounts").authenticated()
                .requestMatchers("/bankaccounts/All").authenticated()
                .requestMatchers("/bankaccounts/{id}").authenticated()
                .requestMatchers("/bankaccounts/dto/{id}").authenticated()
                .requestMatchers("/bankaccounts/info/{id}").authenticated()
                .requestMatchers("/bankaccounts/iban/{iban}").authenticated()
                .requestMatchers("/bankaccounts/userID/{id}").authenticated()
                .requestMatchers("/bankaccounts/change/{id}").authenticated()
                .requestMatchers("/bankaccounts/accountType/{userId}").authenticated();


        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }


}

