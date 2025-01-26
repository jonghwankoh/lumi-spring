package com.jonghwan.typing.domain.my;

import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.shared.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final AuthService authService;
    @GetMapping
    public MyDTO getMy() {
        Member member = authService.getCurrentAuthenticatedUser();

        MyDTO myDTO = new MyDTO();
        myDTO.setEmail(member.getEmail());
        myDTO.setName(member.getName());
        myDTO.setRole(member.getRole());
        log.info("[my] email: {}", member.getEmail());
        return myDTO;
    }
}
