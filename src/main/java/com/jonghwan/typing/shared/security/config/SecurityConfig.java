package com.jonghwan.typing.shared.security.config;

import com.jonghwan.typing.shared.security.jwt.JWTFilter;
import com.jonghwan.typing.shared.security.jwt.JWTUtil;
import com.jonghwan.typing.shared.security.oauth2.CustomSuccessHandler;
import com.jonghwan.typing.shared.security.exception.UnauthorizedEntryPoint;
import com.jonghwan.typing.shared.security.oauth2.CustomOAuth2UserService;
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
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil, UnauthorizedEntryPoint unauthorizedEntryPoint) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        configureStatelessApiServer(httpSecurity);
        configureCors(httpSecurity);
        configureExceptionHandler(httpSecurity);

        configureHttpRequestsAuthorization(httpSecurity);

        configureOAuth2Login(httpSecurity);
        addJWTFilter(httpSecurity);
        return httpSecurity.build();
    }

    private static void configureStatelessApiServer(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);
        httpSecurity
                .formLogin(AbstractHttpConfigurer::disable);
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private static void configureCors(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        config.setAllowedMethods(List.of("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);


                        config.setExposedHeaders(Collections.singletonList("Authorization"));
                        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        return config;
                    }
                }));
    }

    private void configureExceptionHandler(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling((exception) -> exception.authenticationEntryPoint(unauthorizedEntryPoint));
    }

    private static void configureHttpRequestsAuthorization(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/text/{id}", "/text/random").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api-docs").permitAll()
                        .requestMatchers("/my").authenticated()
                        .anyRequest().authenticated());
    }

    private void configureOAuth2Login(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .oauth2Login((oAuth2) -> oAuth2
                        .userInfoEndpoint((config) -> config.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );
    }

    private void addJWTFilter(HttpSecurity httpSecurity) {
        httpSecurity
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);
    }
}
