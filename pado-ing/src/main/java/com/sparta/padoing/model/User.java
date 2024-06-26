package com.sparta.padoing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStamped{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="username")
    private String username;

    @Column(name="name")
    private String name;

//    @Column(name="grade")
//    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;

//    @Column(name="is_active", nullable = false)
//    private boolean isActive;
}
