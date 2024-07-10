package com.example.authorbookrest.entity;

import com.example.authorbookrest.dto.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private LocalDate createdDate;
}
