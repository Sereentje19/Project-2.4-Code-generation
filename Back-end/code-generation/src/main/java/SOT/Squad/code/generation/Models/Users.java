package SOT.Squad.code.generation.Models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String street;
    private int houseNumber;
    private String postalCode;
    private String city;


    public Users(int id, String firstName, String lastName, int phoneNumber, String email, String street, int houseNumber, String postalCode, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
