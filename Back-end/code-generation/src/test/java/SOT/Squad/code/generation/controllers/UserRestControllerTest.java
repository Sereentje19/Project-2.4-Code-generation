package SOT.Squad.code.generation.controllers;

import SOT.Squad.code.generation.jwt.JWTKeyProvider;
import SOT.Squad.code.generation.models.*;
import SOT.Squad.code.generation.models.dto.CurrentUserResponseDTO;
import SOT.Squad.code.generation.models.dto.EditUserRequestDTO;
import SOT.Squad.code.generation.models.dto.UserDropDownDTO;
import SOT.Squad.code.generation.services.BankAccountService;
import SOT.Squad.code.generation.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserRestControllerTest {



    private ObjectMapper objectMapper;



    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();

        objectMapper = new ObjectMapper();
// Mock the JWTKeyProvider using @MockBean

        JWTKeyProvider keyProviderMock = Mockito.mock(JWTKeyProvider.class);


        Mockito.when(keyProviderMock.decodeJWT()).thenReturn("mockedToken");
// Inject the mocked JWTKeyProvider into the controller using @MockBean

        ReflectionTestUtils.setField(userRestController, "keyProvider", keyProviderMock);
    }


//    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private BankAccountService bankAccountService;
    @MockBean
    private JWTKeyProvider keyProvider;

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDropDownDTO> users = new ArrayList<>();
// Add some transactions to the list

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetAllUserIdsAndNames() throws Exception {
        List<UserDropDownDTO> users = new ArrayList<>();
// Add some transactions to the list

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/dropdown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
// Add some transactions to the list

        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

    @Test
    public void testregister() throws Exception {
        User user = new User();
// Add some transactions to the list

        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }




    @Test
    public void testGetUser() throws Exception {
        CurrentUserResponseDTO user = new CurrentUserResponseDTO();
        long id = 1L;
// Add some transactions to the list

        when(userService.getUser(any(long.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }


//    @Test
//    public void testUpdateUser() throws Exception {
//        CurrentUserResponseDTO user = new CurrentUserResponseDTO();
//        User user1 = new User();
//        long id = 1L;
//// Add some transactions to the list
//
//        when(userService.updateUser(id, any(CurrentUserResponseDTO.class))).thenReturn(user1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/users/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//    }
    
    @Test
    public void testCheckPincode() throws Exception {
        String pincode = "4321";
        // Add some transactions to the list

        when(userService.checkPincode(any(String.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/pincode/{pincode}", pincode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testGetUsersWithoutAccounts() throws Exception {
        List<CurrentUserResponseDTO> users = new ArrayList<>();
        // Add some transactions to the list

        when(userService.getUsersWithoutAccounts()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/without-accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserObjectOnUsername() throws Exception {
        String Username = "thijs";
        User user = new User();
        // Add some transactions to the list

        when(userService.getUserObjecttByUsername(Username)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/currentUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserOnUsername() throws Exception {
        String Username = "thijs";
        CurrentUserResponseDTO user = new CurrentUserResponseDTO();
        // Add some transactions to the list

        when(userService.getUserByUsername(Username)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/currentUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}