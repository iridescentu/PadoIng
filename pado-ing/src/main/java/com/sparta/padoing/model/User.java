//package com.sparta.padoing.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "users")
//@NoArgsConstructor
//public class User extends TimeStamped{
//
//    @Id
//    @Column(name = "user_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Column(name="email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name="password")
//    private String password;
//
//    @Column(name="username")
//    private String username;
//
//    @Column(name="name")
//    private String name;
//
////    @Column(name="grade")
////    private String grade;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name="role", nullable = false)
//    private Role role;
//
////    @Column(name="is_active", nullable = false)
////    private boolean isActive;
//}

package com.sparta.padoing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long Id;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    private String username;

    private String name;

    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // private String password;
}