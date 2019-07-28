package com.practice.demoapp.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {


    private static final long serialVersionUID = 2L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus =  false;

    private List<AddressDto> addresses;

    public List<AddressDto> getAddresses() {
        return addresses;
    }

//    public UserDto(long id, String userId, String firstName, String lastName, String email, String password, String encryptedPassword, String emailVerificationToken, Boolean emailVerificationStatus, List<AddressDto> addresses) {
//        this.id = id;
//        this.userId = userId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.encryptedPassword = encryptedPassword;
//        this.emailVerificationToken = emailVerificationToken;
//        this.emailVerificationStatus = emailVerificationStatus;
//        this.addresses = addresses;
//    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVarificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVarificationToken(String emailVarificationToken) {
        this.emailVerificationToken = emailVarificationToken;
    }

    public Boolean getEmailVarificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVarificationStatus(Boolean emailVarificationStatus) {
        this.emailVerificationStatus = emailVarificationStatus;
    }
}
