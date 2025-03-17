package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.base.exception.ForbiddenException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextCommentController {
    private final AuthService authService;
    private final TextCommentRepository repository;
    private final EntityManager em;
    private final int randomNumber = (int) (Math.random() * 200);

    @GetMapping("/text/{textId}/comments")
    public List<TextCommentFetchResponse> getCommentByTextId(@PathVariable Long textId) {
        return repository.findByTypingTextId(textId).stream().map(this::entityToResponse)
                .toList();
    }

    @PostMapping("/text/{textId}/comments")
    public PostResponse postTextComment(@PathVariable Long textId, @RequestBody TextCommentSubmitRequest request) {
        Member member = authService.getCurrentAuthenticatedUser();
        TextComment textComment = new TextComment();
        textComment.setTypingText(em.getReference(TypingText.class, textId));
        textComment.setAuthor(member);
        textComment.setContent(request.getContent());
        repository.save(textComment);

        return PostResponse.of("Comment has been saved", textComment.getId());
    }

    @DeleteMapping("/comments/{commentId}")
    public Response deleteTextComment(@PathVariable Long commentId) {
        Member member = authService.getCurrentAuthenticatedUser();
        TextComment textComment = repository.findById(commentId).orElse(null);
        if (textComment == null) {
            throw new NotFoundException("Comment not found.");
        }
        if (!Objects.equals(textComment.getAuthor().getId(), member.getId())) {
            throw new ForbiddenException("You are not authorized to delete this comment.");
        }

        repository.delete(textComment);
        return Response.of("Comment has been deleted.");
    }

    private TextCommentFetchResponse entityToResponse(TextComment textComment) {

        return TextCommentFetchResponse.builder()
                .id(textComment.getId())
                .likeCount(0L)
                .isLiked(false)
                .authorImg("https://picsum.photos/id/" + ((textComment.getAuthor().getId() + randomNumber) % 200) + "/200/300")
                .authorId(textComment.getAuthor().getId())
                .authorName(textComment.getAuthor().getName())
                .content(textComment.getContent())
                .createdAt(textComment.getCreatedAt())
                .build();
    }
}
