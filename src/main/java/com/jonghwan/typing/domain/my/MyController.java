package com.jonghwan.typing.domain.my;

import com.jonghwan.typing.shared.security.member.Login;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {
    @GetMapping
    public MyDTO getMy(@Login LoginMember loginMember) {
        return MyDTO.builder()
                .memberId(loginMember.id())
                .name(loginMember.name())
                .email(loginMember.email())
                .role(loginMember.role())
                .build();
    }
}
