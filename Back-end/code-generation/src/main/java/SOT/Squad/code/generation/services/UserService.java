package SOT.Squad.code.generation.services;

import SOT.Squad.code.generation.exceptions.UserCreateException;
import SOT.Squad.code.generation.exceptions.UserUpdateException;
import SOT.Squad.code.generation.exceptions.WrongPincodeException;
import SOT.Squad.code.generation.models.dto.EditUserRequestDTO;
import SOT.Squad.code.generation.models.User;
import SOT.Squad.code.generation.models.dto.CurrentUserResponseDTO;
import SOT.Squad.code.generation.models.dto.UserDropDownDTO;
import SOT.Squad.code.generation.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    private List<User> users = new ArrayList<>();
    private final ModelMapper modelMapper;


    public UserService() {
        this.modelMapper = new ModelMapper();
        configureModelMapper();
    }
    private void configureModelMapper() {
        TypeMap<CurrentUserResponseDTO, User> typeMap = modelMapper.getTypeMap(CurrentUserResponseDTO.class, User.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(CurrentUserResponseDTO.class, User.class);
        }
        typeMap.addMappings(mapper -> mapper.skip(User::setPassword));
    }

    public CurrentUserResponseDTO getUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findUserByUsername(username).get();
            CurrentUserResponseDTO currentUserResponseDTO = new CurrentUserResponseDTO();
            currentUserResponseDTO.setId(user.getId());
            currentUserResponseDTO.setFirstName(user.getFirstName());
            currentUserResponseDTO.setLastName(user.getLastName());
            currentUserResponseDTO.setEmail(user.getEmail());
            currentUserResponseDTO.setPhoneNumber(user.getPhoneNumber());
            currentUserResponseDTO.setStreet(user.getStreet());
            currentUserResponseDTO.setCity(user.getCity());
            currentUserResponseDTO.setPostalCode(user.getPostalCode());
            currentUserResponseDTO.setHouseNumber(user.getHouseNumber());
            currentUserResponseDTO.setInActive(user.isInActive());
            currentUserResponseDTO.setRoles(user.getRoles());
            currentUserResponseDTO.setBankAccountList(user.getBankAccountList());

            return currentUserResponseDTO;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public User getUserObjecttByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findUserByUsername(username).get();
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public void checkPasswordStrength(String passw) {
        boolean hasUppercase = !passw.equals(passw.toLowerCase());
        boolean hasLowercase = !passw.equals(passw.toUpperCase());
        boolean hasSpecialCharacters = !passw.matches("[A-Za-z0-9 ]*");
        boolean hasDigits = passw.matches(".*\\d.*");

        if (!hasUppercase) {
            throw new UserCreateException("Password must contain at least one uppercase letter.");
        }else  if (!hasLowercase) {
            throw new UserCreateException("Password must contain at least one lowercase letter.");
        }else if (!hasSpecialCharacters) {
            throw new UserCreateException("Password must contain at least one special character.");
        }else if (!hasDigits) {
            throw new UserCreateException("Password must contain at least one digit.");
        }
    }

    public void checkInputFieldsNotEmpty(User user) {
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new UserCreateException("Password has to be at least 8 characters.");
        } else if (user.getUsername() == null || user.getUsername().length() < 5) {
            throw new UserCreateException("Username has to be at least 5 characters.");
        } else if (user.getPincode() == null || !user.getPincode().matches("\\d{4}")) {
            throw new UserCreateException("Pincode has to be exactly 4 numbers.");
        } else if (!user.getEmail().contains("@")) {
            throw new UserCreateException("Please enter a valid email.");
        } else if (user.getPhoneNumber() == null || user.getPhoneNumber().length() != 10) {
            throw new UserCreateException("Phonenumber has to be exactly 10 numbers");
        } else if (user.getFirstName() == null || user.getLastName() == null
                || user.getPostalCode() == null || user.getCity() == null
                || user.getStreet() == null || !String.valueOf(user.getHouseNumber()).matches("\\d+")) {
            throw new UserCreateException("Please fill all fields before saving all changes.");
        }
    }


    public User addUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new UserCreateException("Username is already taken.");
    }


    public List<UserDropDownDTO> getAllUsers() {

        Iterable<User> users = userRepository.findAll();
        List<UserDropDownDTO> userDropDownDTOList = new ArrayList<>();

        for (User user : users) {
            UserDropDownDTO userDropDownDTO = new UserDropDownDTO();
            userDropDownDTO.setId(user.getId());
            userDropDownDTO.setFirstName(user.getFirstName());
            userDropDownDTO.setLastName(user.getLastName());
            userDropDownDTOList.add(userDropDownDTO);
        }
        return userDropDownDTOList;
    }

    public List<UserDropDownDTO> getAllUserIdsAndNames() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserDropDownDTO> userDropDownDTOList = new ArrayList<>();

        for (User user : users) {
            UserDropDownDTO userDropDownDTO = new UserDropDownDTO();
            userDropDownDTO.setId(user.getId());
            userDropDownDTO.setFirstName(user.getFirstName());
            userDropDownDTO.setLastName(user.getLastName());
            userDropDownDTOList.add(userDropDownDTO);
        }

        return userDropDownDTOList;
    }
    public CurrentUserResponseDTO getUser(long id) {
        User user = userRepository.findById(id).get();
        if (user != null) {
            return modelMapper.map(user, CurrentUserResponseDTO.class);
        }
        throw new UserCreateException("Username is not found.");
    }

    public User getUserObject(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserCreateException("Username is not found.");
    }
    public User updateUser(Long id, CurrentUserResponseDTO user) {
            User userToUpdate = userRepository.findById(id).get();
            if (userToUpdate != null) {
                userToUpdate.setFirstName(user.getFirstName());
                userToUpdate.setLastName(user.getLastName());
                userToUpdate.setEmail(user.getEmail());
                userToUpdate.setPhoneNumber(user.getPhoneNumber());
                userToUpdate.setStreet(user.getStreet());
                userToUpdate.setCity(user.getCity());
                userToUpdate.setPostalCode(user.getPostalCode());
                userToUpdate.setHouseNumber(user.getHouseNumber());
                userToUpdate.setInActive(user.isInActive());
                userToUpdate.setBankAccountList(user.getBankAccountList());
                userToUpdate.setRoles(user.getRoles());
                return userRepository.save(userToUpdate);
            }
        throw new UserCreateException("Username is not found.");
    }
    public boolean checkPincode(String pincode) {
        User user = userRepository.findUserByPincode(pincode);
        if(user == null || user.getId() == 0 || pincode == ""){
            throw new WrongPincodeException("Wrong pincode");
        }
        return true;
    }
    public void UpdateFilledFields(CurrentUserResponseDTO userToUpdate) {
        String phoneNumber = userToUpdate.getPhoneNumber();
        String phoneNumberString = String.valueOf(phoneNumber);
        if (!userToUpdate.getEmail().contains("@")) {
            throw new UserUpdateException("Please enter a valid email.");
        } else if (userToUpdate.getFirstName() == null || userToUpdate.getLastName() == null
                || userToUpdate.getPostalCode() == null || userToUpdate.getCity() == null
                || userToUpdate.getStreet() == null || !String.valueOf(userToUpdate.getHouseNumber()).matches("\\d+")) {
            throw new UserUpdateException("Please fill all fields before saving all changes.");
        }
    }

}
