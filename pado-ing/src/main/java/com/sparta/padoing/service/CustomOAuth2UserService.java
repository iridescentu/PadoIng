//package com.sparta.padoing.service;
//
//import com.sparta.padoing.dto.response.GoogleResponse;
//import com.sparta.padoing.dto.response.OAuth2Response;
//import com.sparta.padoing.dto.UserDTO;
//import com.sparta.padoing.model.CustomOAuth2User;
//import com.sparta.padoing.model.Role;
//import com.sparta.padoing.model.User;
//import com.sparta.padoing.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println(oAuth2User);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        OAuth2Response oAuth2Response = null;
//        if (registrationId.equals("google")) {
//            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
//        } else {
//            return null;
//        }
//
//        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
//        Optional<User> existDataOpt = userRepository.findByUsername(username);
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setName(oAuth2Response.getName());
//        userDTO.setRole(Role.USER);
//
//        if (existDataOpt.isEmpty()) {
//            User user = new User();
//            user.setUsername(username);
//            user.setEmail(oAuth2Response.getEmail());
//            user.setName(oAuth2Response.getName());
//            user.setRole(Role.USER);
//            userRepository.save(user);
//            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
//        } else {
//            User existData = existDataOpt.get();
//            existData.setEmail(oAuth2Response.getEmail());
//            existData.setName(oAuth2Response.getName());
//            userRepository.save(existData);
//            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
//        }
//    }
//}

// User가 여러 권한(role)을 가질 수 있도록 수정
package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.GoogleResponse;
import com.sparta.padoing.dto.response.OAuth2Response;
import com.sparta.padoing.dto.UserDTO;
import com.sparta.padoing.model.CustomOAuth2User;
import com.sparta.padoing.model.Role;
import com.sparta.padoing.model.User;
import com.sparta.padoing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Optional<User> existDataOpt = userRepository.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());

        if (existDataOpt.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setName(oAuth2Response.getName());
            user.addRole(Role.USER);
            userRepository.save(user);
            userDTO.setRoles(Set.of(Role.USER));
            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
        } else {
            User existData = existDataOpt.get();
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());
            userRepository.save(existData);
            userDTO.setRoles(existData.getRoles());
            return new CustomOAuth2User(userDTO, oAuth2User.getAttributes());
        }
    }
}