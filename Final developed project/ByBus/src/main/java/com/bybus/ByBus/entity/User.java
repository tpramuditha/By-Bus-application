package com.bybus.ByBus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique=true, nullable=false)
    private String email;
    private String password;
    private String role;  // ADMIN, DRIVER, PASSENGER

    //For reset password
    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;
}
// Constructors, getters, setters (or use Lombok @Data)