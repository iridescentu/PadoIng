package com.sparta.padoing.dto;

import com.sparta.padoing.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Role role;
    private String name;
    private String username;
    private String token;
}