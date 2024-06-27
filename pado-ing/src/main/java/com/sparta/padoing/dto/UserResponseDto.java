package com.sparta.padoing.dto;

import com.sparta.padoing.model.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String grade;
    private String role;
    private LocalDateTime createdAt;
//    private boolean isActive;
//    private LocalDateTime updatedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.role = user.getRole().name();
        this.createdAt = user.getCreatedAt();
//        this.isActive = user.isActive();
//        this.updatedAt = user.getUpdatedAt();
    }
}
