package com.jonghwan.typing.domain.typingtext.star;

import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.security.member.Login;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TextStarController {
    private final TextStarService starService;

    @PostMapping("/text/{textId}/stars")
    public PostResponse addStar(@Login LoginMember loginMember, @PathVariable Long textId) {
        return starService.addStar(loginMember, textId);
    }

    @DeleteMapping("/text/{textId}/stars")
    public Response unstar(@Login LoginMember loginMember, @PathVariable Long textId) {
        return starService.unstarText(loginMember, textId);
    }
}
