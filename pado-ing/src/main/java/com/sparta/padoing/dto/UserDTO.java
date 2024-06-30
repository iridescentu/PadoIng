//package com.sparta.padoing.dto;
//
//import com.sparta.padoing.model.Role;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class UserDTO {
//    private Role role;
//    private String name;
//    private String username;
//    private String token;
//}

// User가 여러 개의 권한(role)을 가질 수 있도록 수정
package com.sparta.padoing.dto;

import com.sparta.padoing.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Set<Role> roles;
    private String name;
    private String username;
    private String token;
}