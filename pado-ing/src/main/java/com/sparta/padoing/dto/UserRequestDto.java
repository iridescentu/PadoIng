package com.sparta.padoing.dto;

import com.sparta.padoing.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효하지않은 이메일입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "역할은 필수입니다.")
    private Role role;

//    @NotNull(message = "등급은 필수입니다.")
//    private String grade;

    private boolean isActive;
}