package com.jonghwan.typing.shared.security;

import com.jonghwan.typing.shared.security.jwt.JWTUtil;
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
        String adminUsername = "admin";
        String adminRole = "ROLE_ADMIN";
        String adminName = "admin";

        Optional<Member> findMember = memberRepository.findByUsername("admin");

        Member updateMember;
        if (findMember.isEmpty()) {
            // admin 등록
            updateMember = new Member();
            updateMember.setUsername(adminUsername);
            updateMember.setRole(adminRole);
            updateMember.setName(adminName);
            updateMember.setEmail(adminEmail);
            memberRepository.save(updateMember);
        }
        String adminToken = jwtUtil.createJwt(adminUsername, adminRole, 1000L*3600*24);
        log.info("""
                
                ====================
                 Admin JWT Token:\s
                 {}
                ====================
                """, adminToken);
    }
}
