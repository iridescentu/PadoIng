//package com.sparta.padoing.dto.response;
//
//import com.sparta.padoing.model.User;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//@Getter
//public class UserResponseDto {
//    private Long id;
//    private String email;
//    private String grade;
//    private String role;
//    private LocalDateTime createdAt;
////    private boolean isActive;
////    private LocalDateTime updatedAt;
//
//    public UserResponseDto(User user) {
//        this.id = user.getId();
//        this.email = user.getEmail();
//        this.grade = user.getGrade();
//        this.role = user.getRole().name();
//        this.createdAt = user.getCreatedAt();
////        this.isActive = user.isActive();
////        this.updatedAt = user.getUpdatedAt();
//    }
//}


// User의 민감한 정보(role, email, username 등)를 숨기고 name만 공개되도록
package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String name;

    public UserResponseDto(User user) {
        this.name = user.getName();
    }
}