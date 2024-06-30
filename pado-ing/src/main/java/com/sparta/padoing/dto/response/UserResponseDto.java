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