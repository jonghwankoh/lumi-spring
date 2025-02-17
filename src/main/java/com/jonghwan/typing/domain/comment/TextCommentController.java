package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/text/{textId}/comments")
@RequiredArgsConstructor
public class TextCommentController {
    private final AuthService authService;
    private final TextCommentRepository repository;
    private final EntityManager em;
    private final int randomNumber = (int) (Math.random() * 200);

    @GetMapping
    public List<TextCommentFetchResponse> getCommentByTextId(@PathVariable Long textId) {
        return repository.findByTypingTextId(textId).stream().map(this::entityToResponse)
                .toList();
    }

    @PostMapping
    public PostResponse postTextComment(@PathVariable Long textId, @RequestBody TextCommentSubmitRequest request) {
        Member member = authService.getCurrentAuthenticatedUser();
        TextComment textComment = new TextComment();
        textComment.setTypingText(em.getReference(TypingText.class, textId));
        textComment.setAuthor(member);
        textComment.setContent(request.getContent());
        repository.save(textComment);

        return new PostResponse(true, "Comment has been saved.", textComment.getId());
    }

    private TextCommentFetchResponse entityToResponse(TextComment textComment) {
        TextCommentFetchResponse response = new TextCommentFetchResponse();
        response.setId(textComment.getId());
        response.setLikeNum(0);
        response.setIsLiked(false);
        response.setAuthorImg("https://picsum.photos/id/"+ (textComment.getAuthor().getId() + randomNumber) % 200 +"/200/300");
        response.setAuthorId(textComment.getAuthor().getId());
        response.setAuthorName(textComment.getAuthor().getName());
        response.setContent(textComment.getContent());
        response.setCreatedAt(textComment.getCreatedAt());
        return response;
    }
}
