package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.shared.base.dto.DeleteResponse;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextLikeController {
    private final TextLikeService textLikeService;

    @PostMapping("/text/{textId}/likes")
    public PostResponse addLike(@PathVariable Long textId) {
        return textLikeService.addLike(textId);
    }

    @DeleteMapping("/text/{textId}/likes")
    public DeleteResponse unlike(@PathVariable Long textId) {
        return textLikeService.unlikeText(textId);
    }
}
