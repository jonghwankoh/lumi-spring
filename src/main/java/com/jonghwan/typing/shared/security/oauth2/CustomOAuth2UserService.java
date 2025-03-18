package com.jonghwan.typing.shared.security.oauth2;

import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.shared.security.MemberRepository;
import com.jonghwan.typing.shared.security.dto.CustomOAuth2User;
import com.jonghwan.typing.shared.security.dto.GoogleResponse;
import com.jonghwan.typing.shared.security.dto.AuthUserDTO;
import com.jonghwan.typing.shared.security.dto.OAuth2Response;
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

        Member currentMember;
        if (findMember.isEmpty()) {
            // 회원가입
            currentMember = Member.builder()
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .build();

        } else {
            // 기존 로그인
            currentMember = findMember.get();
            // 이메일, 이름 등록 및 업데이트
            currentMember.updateName(oAuth2Response.getName());
            currentMember.updateEmail(oAuth2Response.getEmail());
        }
        Member savedMember = memberRepository.save(currentMember);

        // 유저정보 반환
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setUsername(savedMember.getUsername());
        authUserDTO.setName(savedMember.getName());
        authUserDTO.setRole(savedMember.getRole());
        return new CustomOAuth2User(authUserDTO);
    }
}
