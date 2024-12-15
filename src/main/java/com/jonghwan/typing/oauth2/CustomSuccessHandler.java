package com.jonghwan.typing.oauth2;

import com.jonghwan.typing.dto.CustomOAuth2User;
import com.jonghwan.typing.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*1000L);
        Cookie jwtCookie = createJwtCookie(token);

        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:5173/success?status=success&username=" + userDetails.getUsername());
    }

    private Cookie createJwtCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
