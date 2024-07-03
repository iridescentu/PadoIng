// User가 여러 개의 권한(role)을 가질 수 있도록 수정
package com.sparta.padoing.jwt;

import com.sparta.padoing.dto.UserDTO;
import com.sparta.padoing.model.CustomOAuth2User;
import com.sparta.padoing.model.Role;
import com.sparta.padoing.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // OAuth2 인증 경로는 필터링하지 않음
        String path = request.getRequestURI();
        return path.startsWith("/oauth2/authorization/google");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 Authorization 토큰을 가져옴
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                }
            }
        }

        // 토큰이 없으면 필터링 통과
        if (authorization == null) {
            System.out.println("Token is null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;
        System.out.println("Token: " + token);

        // 토큰이 만료되었으면 필터링 통과
        if (jwtUtil.isExpired(token)) {
            System.out.println("Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자명과 역할을 가져옴
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        System.out.println("Username from token: " + username);
        System.out.println("Role from token: " + role);

        // 사용자 정보를 설정하고 SecurityContext에 저장
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRoles(Collections.singleton(Role.valueOf(role)));

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO, jwtUtil.getClaims(token));

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        System.out.println("AuthToken: " + authToken);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("SecurityContextHolder setAuthentication: " + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }
}