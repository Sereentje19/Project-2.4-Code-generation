package SOT.Squad.code.generation.Controllers;

import SOT.Squad.code.generation.JWT.JWTKeyProvider;
import SOT.Squad.code.generation.JWT.JWTTokenProvider;
import SOT.Squad.code.generation.Models.DTO.ErrorDTO;
import SOT.Squad.code.generation.Models.DTO.LoginRequestDTO;
import SOT.Squad.code.generation.Models.DTO.LoginResponseDTO;
import SOT.Squad.code.generation.Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;

public class LoginRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginRestController loginRestController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginRestController).build();
        objectMapper = new ObjectMapper();

        // Mock the JWTKeyProvider using @MockBean
        JWTKeyProvider keyProviderMock = Mockito.mock(JWTKeyProvider.class);
        when(keyProviderMock.decodeJWT()).thenReturn("mockedToken");

        // Inject the mocked JWTKeyProvider into the controller using ReflectionTestUtils
        ReflectionTestUtils.setField(loginRestController, "tokenProvider", keyProviderMock);
    }

    @Test
    void testLogin_ValidCredentials() throws Exception {
        // Mock the service response
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUsername("serena");
        requestDTO.setPassword("kenter");

        LoginResponseDTO responseDTO = new LoginResponseDTO("token");
        when(userService.login(any(LoginRequestDTO.class))).thenReturn(responseDTO);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("token"));
    }

//    @Test
//    void testLogin_InvalidCredentials() throws Exception {
//        // Mock the service throwing an IllegalArgumentException
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUsername("WrongUsername");
//        requestDTO.setPassword("WrongPassword");
//
//        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new IllegalArgumentException("Invalid username or password"));
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
//        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new RuntimeException("An error occurred"));
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
