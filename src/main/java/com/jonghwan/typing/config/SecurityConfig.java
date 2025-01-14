package com.jonghwan.typing.config;

import com.jonghwan.typing.jwt.JWTFilter;
import com.jonghwan.typing.jwt.JWTUtil;
import com.jonghwan.typing.oauth2.CustomSuccessHandler;
import com.jonghwan.typing.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Stateless API 서버 기본 설정
        httpSecurity.cors(cors -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);


                        config.setExposedHeaders(Collections.singletonList("Authorization"));
                        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        return config;
                    }
                }));
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);
        httpSecurity
                .formLogin(AbstractHttpConfigurer::disable);
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/text/{id}", "/text/random", "/api-docs").permitAll()
                        .requestMatchers("/my").authenticated()
                        .anyRequest().authenticated());

        // oauth2UserService 등록
        httpSecurity
                .oauth2Login((oAuth2) -> oAuth2
                        .userInfoEndpoint((config) -> config.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );

        httpSecurity
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
