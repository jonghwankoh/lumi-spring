package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.shared.base.dto.DeleteResponse;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        return new PostResponse(true, "Comment has been saved.", textComment.getId());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<DeleteResponse> deleteTextComment(@PathVariable Long commentId) {
        Member member = authService.getCurrentAuthenticatedUser();
        TextComment textComment = repository.findById(commentId).orElse(null);
        if (textComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DeleteResponse(false, "Comment not found."));
        }

        if (!Objects.equals(textComment.getAuthor().getId(), member.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new DeleteResponse(false, "You are not authorized to delete this comment."));
        }

        repository.delete(textComment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
