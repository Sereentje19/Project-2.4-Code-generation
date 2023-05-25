package SOT.Squad.code.generation.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/users/login")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");

//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("*");

        registry.addMapping("/transactions/{id}")
                .allowedOrigins("http://localhost:5174")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

}
