package com.jonghwan.typing.shared.security;

import com.jonghwan.typing.shared.security.jwt.JWTUtil;
import com.jonghwan.typing.shared.security.member.Member;
import com.jonghwan.typing.shared.security.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class AdminTokenPrinter implements ApplicationRunner {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Override
    public void run(ApplicationArguments args) {
        String adminUserKey = "admin";
        String adminRole = "ROLE_ADMIN";
        String adminName = "admin";

        Optional<Member> findMember = memberRepository.findByUserKey(adminUserKey);

        Member adminEntity;
        adminEntity = findMember.orElseGet(() -> memberRepository.save(Member.builder()
                .userKey(adminUserKey)
                .role(adminRole)
                .name(adminName)
                .email(adminEmail)
                .build()));
        String adminToken = jwtUtil.createJwt(adminEntity.getId().toString(), adminRole, 1000L*3600*24);
        log.info("""
                
                ====================
                 Admin JWT Token:\s
                 Authorization={}; Path=/; HttpOnly;
                ====================
                """, adminToken);
    }
}
