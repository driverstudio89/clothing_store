package bg.softuni.clothing_store.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class UserProfileDto {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    private String zip;

    public String getUsername() {
        return username;
    }

    public UserProfileDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserProfileDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserProfileDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserProfileDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserProfileDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public UserProfileDto setZip(String zip) {
        this.zip = zip;
        return this;
    }
}
