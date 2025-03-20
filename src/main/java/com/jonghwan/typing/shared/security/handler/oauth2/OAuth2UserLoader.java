package com.jonghwan.typing.shared.security.handler.oauth2;

import com.jonghwan.typing.shared.security.member.Member;
import com.jonghwan.typing.shared.security.member.MemberRepository;
import com.jonghwan.typing.shared.security.dto.oatuh2.LoadedOAuth2User;
import com.jonghwan.typing.shared.security.dto.oatuh2.response.GoogleResponse;
import com.jonghwan.typing.shared.security.dto.oatuh2.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuth2UserLoader extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Oauth2 제공자
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Oauth2 사용자 정보 매핑
        OAuth2Response oAuth2Response;
        switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
//            case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + registrationId);
        }

        // userKey 생성
        String userKey = oAuth2Response.getProviderType() + "_" + oAuth2Response.getProviderSubject();
        String name = oAuth2Response.getName();
        String email = oAuth2Response.getEmail();

        // 기존 회원 조회 또는 신규 회원 생성
        Member loadedMember = memberRepository.findByUserKey(userKey)
                .orElseGet(() -> Member.builder()
                        .userKey(userKey)
                        .name(name)
                        .email(email)
                        .role("ROLE_USER")
                        .build());

        // 사용자 정보 업데이트
        loadedMember.mergeUserInfo(name, email);
        Member savedMember = memberRepository.save(loadedMember);

        return new LoadedOAuth2User(savedMember);
    }
}
