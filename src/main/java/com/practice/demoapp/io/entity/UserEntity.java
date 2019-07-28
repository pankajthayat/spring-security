package com.practice.demoapp.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "USERS")
public class UserEntity implements Serializable {

    private static  final long serialVersionUID = 1L;// change it

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
//    @Column(nullable = false, length = 120, unique = true) //unique is to avoid duplicate entry... we will check it manually here
//    private String email;
    @Column(nullable = false, length = 120) //unique is to avoid duplicate entry
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;
    private String emailVerificationToken;

    // here we are not setting default value by column def....its not portable to all the db...so explicity set the default...@Column(nullable = false, columnDefinition = "boolean default false")
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;


    //UserEntity is parent here...will be stored in dababase and bcoz userEntity contain Addess we can call... addess are owned by userDetails
    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL ) // name of the field that own in the relationship
    private List<AddressEntity> addresses; //CascadeType.ALL =>operation will propagate to list of Address as well

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

//    public static long getSerialVerionUID() {
//        return serialVerionUID;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }
}
