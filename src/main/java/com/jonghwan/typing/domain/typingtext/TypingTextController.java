package com.jonghwan.typing.domain.typingtext;

import com.jonghwan.typing.domain.typingtext.dto.TypingTextFetchResponse;
import com.jonghwan.typing.shared.security.member.Login;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TypingTextController {
    private final TypingTextService textService;

    @GetMapping("/text/{id}")
    public TypingTextFetchResponse getTextById(@Login LoginMember loginMember, @PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean fullDetails) {
        return textService.getTypingText(loginMember, id, fullDetails);
    }

    @GetMapping("/text/random")
    public TypingTextFetchResponse getRandomText() {
        return textService.getRandomText();
    }
}
