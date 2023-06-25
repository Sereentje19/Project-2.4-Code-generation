package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.exceptions.InvalidCredentialsException;
import SOT.Squad.code.generation.models.dto.LoginRequestDTO;
import SOT.Squad.code.generation.models.dto.LoginResponseDTO;
import SOT.Squad.code.generation.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class LoginRestControllerTest {

    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginRestController loginRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testLoginValidCredentials() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("username");
        loginRequestDTO.setPassword("password");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("token");

        when(loginService.login(loginRequestDTO)).thenReturn(loginResponseDTO);

        ResponseEntity<?> responseEntity = loginRestController.login(loginRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(loginResponseDTO, responseEntity.getBody());
        verify(loginService, times(1)).login(loginRequestDTO);
    }

    @Test
    void testLoginInvalidCredentials() {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("username");
        requestDTO.setPassword("password");

        when(loginService.login(requestDTO)).thenThrow(new InvalidCredentialsException("Invalid username or password"));

        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid username or password", responseEntity.getBody());
        verify(loginService, times(1)).login(requestDTO);
    }
}
