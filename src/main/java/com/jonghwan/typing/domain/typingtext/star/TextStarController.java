package com.jonghwan.typing.domain.typingtext.star;

import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextStarController {
    private final TextStarService starService;

    @PostMapping("/text/{textId}/stars")
    public PostResponse addStar(@PathVariable Long textId) {
        return starService.addStar(textId);
    }

    @DeleteMapping("/text/{textId}/stars")
    public Response unstar(@PathVariable Long textId) {
        return starService.unstarText(textId);
    }
}
