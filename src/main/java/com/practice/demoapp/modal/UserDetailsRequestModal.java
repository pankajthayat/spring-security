package com.practice.demoapp.modal;

import com.practice.demoapp.modal.request.AddressRequestModal;

import java.util.List;

public class UserDetailsRequestModal {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<AddressRequestModal> addresses;

    public UserDetailsRequestModal(String firstName, String lastName, String email, String password, List<AddressRequestModal> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
    }

    public List<AddressRequestModal> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequestModal> addresses) {
        this.addresses = addresses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
