package com.jonghwan.typing.jwt;

import com.jonghwan.typing.dto.CustomOAuth2User;
import com.jonghwan.typing.dto.MemberDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            log.info("No cookies");
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                token = cookie.getValue();
            }
        }

        if (token == null) {
            log.info("token null");
            filterChain.doFilter(request, response);
            return;
        } else if (jwtUtil.isExpired(token)) {
            log.info("token expired");
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // UsernamePasswordAuthenticationToken 생성
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUsername(username);
        memberDTO.setRole(role);
        CustomOAuth2User details = new CustomOAuth2User(memberDTO);
        Authentication authToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

        // 세션에 인증정보 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}