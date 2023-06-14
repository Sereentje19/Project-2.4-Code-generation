package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.JWT.JWTTokenProvider;
import SOT.Squad.code.generation.Models.DTO.ErrorDTO;
import SOT.Squad.code.generation.Models.DTO.LoginRequestDTO;
import SOT.Squad.code.generation.Models.DTO.LoginResponseDTO;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.LoginService;
import SOT.Squad.code.generation.Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class LoginRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginRestController loginRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testLogin_ValidCredentials() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("serena");
        loginRequestDTO.setPassword("kenter");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("token");

        when(loginService.login(loginRequestDTO)).thenReturn(loginResponseDTO);

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(loginRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(loginResponseDTO, responseEntity.getBody());
        verify(loginService, times(1)).login(loginRequestDTO);
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("wrongUsername");
        requestDTO.setPassword("WrongPassword");

        when(loginService.login(requestDTO)).thenThrow(new IllegalArgumentException("Invalid username or password"));

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Invalid username or password", ((LoginResponseDTO) responseEntity.getBody()).getToken());
        verify(loginService, times(1)).login(requestDTO);
    }

    @Test
    void testLogin_InternalServerError() {
        // Arrange
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("serena");
        requestDTO.setPassword("kenter");

        when(loginService.login(requestDTO)).thenThrow(new RuntimeException("An error occurred"));

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred", ((LoginResponseDTO) responseEntity.getBody()).getToken());
        verify(loginService, times(1)).login(requestDTO);
    }


}
