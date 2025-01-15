package com.jonghwan.typing.service;

import com.jonghwan.typing.dto.*;
import com.jonghwan.typing.entity.Member;
import com.jonghwan.typing.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        // DB 조회/저장
        Optional<Member> findMember = memberRepository.findByUsername(username);

        Member updateMember;
        if (findMember.isEmpty()) {
            // 회원가입
            updateMember = new Member();
            updateMember.setUsername(username);
            updateMember.setRole("ROLE_USER");
        } else {
            // 기존 로그인
            updateMember = findMember.get();
        }
        // 이메일, 이름 등록 및 업데이트
        updateMember.setName(oAuth2Response.getName());
        updateMember.setEmail(oAuth2Response.getEmail());
        Member savedMember = memberRepository.save(updateMember);

        // 유저정보 반환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUsername(savedMember.getUsername());
        memberDTO.setName(savedMember.getName());
        memberDTO.setRole(savedMember.getRole());
        return new CustomOAuth2User(memberDTO);
    }
}
