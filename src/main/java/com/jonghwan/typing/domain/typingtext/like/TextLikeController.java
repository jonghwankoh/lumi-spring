package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TextLikeController {
    private final TextLikeService textLikeService;

    @PostMapping("/text/{textId}/likes")
    public PostResponse addLike(@PathVariable Long textId) {
        return textLikeService.addLike(textId);
    }

    @DeleteMapping("/text/{textId}/likes")
    public Response unlike(@PathVariable Long textId) {
        return textLikeService.unlikeText(textId);
    }
}
