//package com.sparta.padoing.oauth2;
//
//import com.sparta.padoing.model.CustomOAuth2User;
//import com.sparta.padoing.util.JWTUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import jakarta.servlet.http.Cookie;
//import java.io.IOException;
//
//import java.util.Collection;
//import java.util.Iterator;
//
//@Component
//public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JWTUtil jwtUtil;
//
//    public CustomSuccessHandler(JWTUtil jwtUtil) {
//
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
//
//        //OAuth2User
//        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
//
//        String username = customUserDetails.getUsername();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        String role = auth.getAuthority();
//
//        // JWT 토큰 생성
//        String token = jwtUtil.createJwt(username, role, 60*60*60L);
//
//        // 쿠키에 토큰 추가
//        response.addCookie(createCookie("Authorization", token));
//        response.sendRedirect("http://localhost:8080");
//    }
//
//    private Cookie createCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        //cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }
//}

package com.sparta.padoing.oauth2;

import com.sparta.padoing.model.CustomOAuth2User;
import com.sparta.padoing.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        // 여러 개의 권한을 문자열로 결합
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            if (roles.length() > 0) {
                roles.append(",");
            }
            roles.append(authority.getAuthority());
        }

        // JWT 토큰 생성
        String token = jwtUtil.createJwt(username, roles.toString(), 60*60*60L);

        // 쿠키에 토큰 추가
        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect("http://localhost:8080");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        // cookie.setSecure(true); // HTTPS 사용하는 경우에만 설정
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}