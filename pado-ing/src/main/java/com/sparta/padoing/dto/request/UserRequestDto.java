package com.sparta.padoing.dto.request;

import com.sparta.padoing.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    private String givenName;

    @NotNull(message = "역할은 필수입니다.")
    private Role role;

    private String grade;
}