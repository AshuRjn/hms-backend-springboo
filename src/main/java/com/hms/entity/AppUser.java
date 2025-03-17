package com.hms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 4 and 20 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "role", nullable = false, length = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 500)
    private String password;

//    public void setPhoneNumber(String phoneNumber) {
//        if (phoneNumber != null && !phoneNumber.startsWith("+")) {
//            this.phoneNumber = "+91" + phoneNumber.trim(); // Trim spaces & add +91 if missing
//        } else {
//            this.phoneNumber = phoneNumber;
//        }
//    }


}