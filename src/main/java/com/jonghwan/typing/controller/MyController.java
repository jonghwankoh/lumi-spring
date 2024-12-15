package com.jonghwan.typing.controller;

import com.jonghwan.typing.dto.CustomOAuth2User;
import com.jonghwan.typing.dto.MyDTO;
import com.jonghwan.typing.entity.UserEntity;
import com.jonghwan.typing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final UserRepository userRepository;
    @GetMapping
    public MyDTO getMy() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않았을리 없어.");
        }
        if (auth.getPrincipal() instanceof CustomOAuth2User customUser) {
            MyDTO myDTO = new MyDTO();
            UserEntity user = userRepository.findByUsername(customUser.getUsername());
            myDTO.setEmail(user.getEmail());
            myDTO.setName(user.getName());
            myDTO.setRole(user.getRole());
            log.info("[my] email: {}", user.getEmail());
            return myDTO;
        }
        throw new IllegalStateException("CustomOAuth2User가 아닐리 없어.");
    }
}
