package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.JWT.JWTTokenProvider;
import SOT.Squad.code.generation.Models.DTO.ErrorDTO;
import SOT.Squad.code.generation.Models.DTO.LoginRequestDTO;
import SOT.Squad.code.generation.Models.DTO.LoginResponseDTO;
import SOT.Squad.code.generation.Models.Role;
import SOT.Squad.code.generation.Models.User;
import SOT.Squad.code.generation.Services.UserDetailService;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LoginRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private UserService userServiceMock;
    @InjectMocks
    private LoginRestController loginRestController;

    @Mock
    private JWTTokenProvider tokenProviderMock;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        loginRestController = new LoginRestController();
        ReflectionTestUtils.setField(loginRestController, "userService", userServiceMock);
        ReflectionTestUtils.setField(loginRestController, "tokenProvider", tokenProviderMock);
    }

    @Test
    public void login_ValidCredentials_ReturnsOkResponse() {
        // Arrange
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("serena");
        requestDTO.setPassword("kenter");
        LoginResponseDTO expectedResponse = new LoginResponseDTO("Success");

        when(userServiceMock.login(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(userServiceMock, times(1)).login(requestDTO);
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    public void login_InvalidCredentials_ReturnsForbiddenResponse() {
        // Arrange
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("WrongUsername");
        requestDTO.setPassword("WrongPassword");
        String errorMessage = "Invalid username or password";
        ErrorDTO expectedError = new ErrorDTO(errorMessage);

        when(userServiceMock.login(requestDTO)).thenThrow(new IllegalArgumentException(errorMessage));

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Object responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        if (responseBody instanceof ErrorDTO) {
            assertEquals(expectedError.getMessage(), ((ErrorDTO) responseBody).getMessage());
        } else if (responseBody instanceof LoginResponseDTO) {
            fail("Expected error response, but received successful login response.");
        } else {
            fail("Unexpected response body type.");
        }
        verify(userServiceMock, times(1)).login(requestDTO);
        verifyNoMoreInteractions(userServiceMock);
    }



    @Test
    public void login_ExceptionThrown_ReturnsInternalServerErrorResponse() {
        // Arrange
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("serena");
        requestDTO.setPassword("kenter");

        when(userServiceMock.login(requestDTO)).thenThrow(new RuntimeException("Some error occurred"));

        // Act
        ResponseEntity<?> responseEntity = loginRestController.login(requestDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred", ((ErrorDTO) responseEntity.getBody()).getMessage());
        verify(userServiceMock, times(1)).login(requestDTO);
        verifyNoMoreInteractions(userServiceMock);
    }


//
//    @Test
//    void testLogin_ValidCredentials() throws Exception {
//        // Mock the service response
//        new User(1, "serena", "kenter", "Serena", "Kenter", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp", null,true, List.of(Role.EMPLOYEE), "3685",2000,300);
//
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUsername("serena");
//        requestDTO.setPassword("kenter");
//
//        LoginResponseDTO responseDTO = new LoginResponseDTO("token");
//        when(userService.login(any(LoginRequestDTO.class))).thenReturn(responseDTO);
//
//        // Perform the request and assert the response
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.token").value("token"));
//    }
//
//    @Test
//    void testLogin_InvalidCredentials() throws Exception {
//        // Mock the service throwing an IllegalArgumentException
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUsername("WrongUsername");
//        requestDTO.setPassword("WrongPassword");
//
//        when(userService.login(any(LoginRequestDTO.class))).thenThrow(new IllegalArgumentException("Invalid username or password"));
//
//        // Perform the request and assert the response
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
//                .andExpect(status().isForbidden())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Invalid username or password"));
//    }
//
//
//    @Test
//    void testLogin_InternalServerError() throws Exception {
//        // Mock the service throwing an exception
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUsername("username");
//        requestDTO.setPassword("password");
//
//        when(userService.login(any(LoginRequestDTO.class))).thenThrow(new RuntimeException("An error occurred"));
//
//        // Perform the request and assert the response
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.token").value("An error occurred"));
//                .andExpect(jsonPath("$.message").value("An error occurred"));
//    }

}
