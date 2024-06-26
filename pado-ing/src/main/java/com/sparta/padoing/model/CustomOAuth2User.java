// User가 여러 개의 권한(role)을 가질 수 있도록 수정
package com.sparta.padoing.model;

import com.sparta.padoing.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User {
    private final UserDTO userDTO;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(UserDTO userDTO, Map<String, Object> attributes) {
        this.userDTO = userDTO;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDTO.getRoles().stream()
                .map(role -> (GrantedAuthority) role::name)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return userDTO.getUsername();
    }

    public String getUsername() {
        return userDTO.getUsername();
    }
}