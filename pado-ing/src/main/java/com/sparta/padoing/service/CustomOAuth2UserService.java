//package com.sparta.padoing.service;
//
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        // 구글 사용자 정보 가져오기
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 사용자 정보 처리 (필요에 따라 사용자 정보를 저장하거나 추가 로직을 작성)
//        // 예: 구글 프로필 정보를 사용하여 사용자 엔티티를 생성 또는 업데이트
//
//        return oAuth2User;
//    }
//}

package com.sparta.padoing.service;

import com.sparta.padoing.dto.GoogleResponse;
import com.sparta.padoing.dto.OAuth2Response;
import com.sparta.padoing.dto.UserDTO;
import com.sparta.padoing.model.CustomOAuth2User;
import com.sparta.padoing.model.Role;
import com.sparta.padoing.model.User;
import com.sparta.padoing.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if (existData == null) {

            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setName(oAuth2Response.getName());
            user.setRole(Role.USER);

            userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(Role.USER);

            return new CustomOAuth2User(userDTO);
        }
        else {
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}