package com.userservice.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userservice.entities.role.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "custom_user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Column(name = "email_id", length = 325, unique = true, nullable = false)
    private String emailId;

    @Column(name = "mobile_number", length = 10, unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @Column(name = "registration_datetime", nullable = false)
    private LocalDateTime registrationDatetime;

}
