package com.ttknpdev.backend.entities.secure;

import jakarta.persistence.*;
import lombok.Data;
// for login
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String username;
    private String password;
    private String roles;
}