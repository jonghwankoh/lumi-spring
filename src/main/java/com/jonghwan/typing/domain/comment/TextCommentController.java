package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.security.member.Login;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TextCommentController {
    private final TextCommentService textCommentService;

    @GetMapping("/text/{textId}/comments")
    public List<TextCommentFetchResponse> getCommentByTextId(@PathVariable Long textId) {
        return textCommentService.getCommentByTextId(textId);
    }

    @PostMapping("/text/{textId}/comments")
    public PostResponse postTextComment(@Login LoginMember loginMember, @PathVariable Long textId, @RequestBody TextCommentSubmitRequest request) {
        return textCommentService.postTextComment(loginMember, textId, request);
    }

    @DeleteMapping("/comments/{commentId}")
    public Response deleteTextComment(@Login LoginMember loginMember,@PathVariable Long commentId) {
        return textCommentService.deleteTextComment(loginMember, commentId);
    }
}
