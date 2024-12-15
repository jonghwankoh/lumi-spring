package com.jonghwan.typing.service;

import com.jonghwan.typing.dto.*;
import com.jonghwan.typing.entity.UserEntity;
import com.jonghwan.typing.repository.UserRepository;
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

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        // DB 조회/저장
        UserEntity findUser = userRepository.findByUsername(username);

        UserEntity updateUser;
        if (findUser == null) {
            // 회원가입
            updateUser = new UserEntity();
            updateUser.setUsername(username);
            updateUser.setRole("ROLE_USER");
        } else {
            // 기존 로그인
            updateUser = findUser;
        }
        // 이메일, 이름 등록 및 업데이트
        updateUser.setName(oAuth2Response.getName());
        updateUser.setEmail(oAuth2Response.getEmail());
        UserEntity savedUser = userRepository.save(updateUser);

        // 유저정보 반환
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(savedUser.getUsername());
        userDTO.setName(savedUser.getName());
        userDTO.setRole(savedUser.getRole());
        return new CustomOAuth2User(userDTO);
    }
}
