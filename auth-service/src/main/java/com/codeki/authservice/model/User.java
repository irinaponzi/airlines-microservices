package com.codeki.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String passport;
    private String dni;

    public User(String name, String lastName, String passport, String dni) {
        this.name = name;
        this.lastName = lastName;
        this.passport = passport;
        this.dni = dni;
    }
}
