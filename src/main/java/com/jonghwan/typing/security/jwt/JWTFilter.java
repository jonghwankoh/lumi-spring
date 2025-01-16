package com.jonghwan.typing.security.jwt;

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
        String jwt = getJWTFromCookies(request);
        log.info("JWT: {}", jwt);
        if (jwt == null) {
            log.info("jwt not exist");
        } else if (jwtUtil.isExpired(jwt)) {
            log.info("jwt expired");
        } else {
            setAuthToken(jwt);
        }
        filterChain.doFilter(request, response);
    }

    private static String getJWTFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return null;
        for (Cookie cookie : cookies) {
            log.info("cookie: {}", cookie.getValue());
            if (cookie.getName().equals("Authorization")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void setAuthToken(String token) {
        JwtUserDetails userDetails = new JwtUserDetails(jwtUtil.getUsername(token), jwtUtil.getRole(token));

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}