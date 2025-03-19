package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.base.exception.ForbiddenException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.constant.RandomNumberProvider;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextCommentService {
    private final AuthService authService;
    private final TextCommentRepository repository;
    private final TypingTextRepository textRepository;
    private final RandomNumberProvider randomNumberProvider;

    @Transactional(readOnly = true)
    public List<TextCommentFetchResponse> getCommentByTextId(Long textId) {
        return repository.findByTypingTextId(textId).stream().map(this::entityToResponse)
                .toList();
    }

    @Transactional
    public PostResponse postTextComment(Long textId, TextCommentSubmitRequest request) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingText text = textRepository.findById(textId)
                .orElseThrow(() -> new NotFoundException("text not found"));
        TextComment textComment = TextComment.builder()
                .typingText(text)
                .author(member)
                .content(request.content())
                .build();
        repository.save(textComment);

        return PostResponse.of("Comment has been saved", textComment.getId());
    }

    @Transactional
    public Response deleteTextComment(Long commentId) {
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
                .likeCount(0L) // TODO: TextCommentLike
                .isLiked(false) // TODO: TextCommentLike
                // TODO: author Image
                .authorImg("https://picsum.photos/id/" + ((textComment.getAuthor().getId() + randomNumberProvider.getRandomNumber()) % 200) + "/200/300")
                .authorId(textComment.getAuthor().getId())
                .authorName(textComment.getAuthor().getName())
                .content(textComment.getContent())
                .createdAt(textComment.getCreatedAt())
                .build();
    }
}
