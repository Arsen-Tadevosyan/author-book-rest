package com.example.authorbookrest.entity;

import com.example.authorbookrest.dto.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String imagePath;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
