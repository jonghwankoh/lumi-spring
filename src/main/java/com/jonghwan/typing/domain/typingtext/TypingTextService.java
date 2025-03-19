package com.jonghwan.typing.domain.typingtext;

import com.jonghwan.typing.domain.comment.TextCommentRepository;
import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.typingtext.dto.TypingTextFetchResponse;
import com.jonghwan.typing.domain.typingtext.like.TextLikeRepository;
import com.jonghwan.typing.domain.typingtext.star.TextStarRepository;
import com.jonghwan.typing.shared.exception.custom.NotFoundException;
import com.jonghwan.typing.shared.constant.RandomNumberProvider;
import com.jonghwan.typing.shared.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypingTextService {
    private final AuthService authService;
    private final TypingTextRepository typingTextRepository;
    private final TextLikeRepository likeRepository;
    private final TextStarRepository starRepository;
    private final TextCommentRepository commentRepository;

    private final RandomNumberProvider randomNumberProvider;

    @Transactional(readOnly = true)
    public TypingTextFetchResponse getTypingText(Long textId, boolean fullDetails) {
        TypingText text = typingTextRepository.findById(textId)
                .orElseThrow(() -> new NotFoundException("Text not found"));

        if(!fullDetails) {
            return TypingTextFetchResponse.builder()
                    .id(text.getId())
                    .title(text.getTitle())
                    .content(text.getContent())
                    .build();
        }
        Long memberId = authService.getCurrentAuthenticatedUser().getId();
        Long likeCount = likeRepository.countByTypingTextId(textId);
        boolean isLiked = likeRepository.existsByTypingTextIdAndMemberId(textId, memberId);
        boolean isStarred = starRepository.existsByTypingTextIdAndMemberId(textId, memberId);

        List<TextCommentFetchResponse> comments = commentRepository.findByTypingTextId(textId).stream()
                .map(c -> TextCommentFetchResponse.builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .authorId(c.getAuthor().getId())
                        .authorName(c.getAuthor().getName())
                        .authorImg("https://picsum.photos/id/" + ((c.getAuthor().getId() + randomNumberProvider.getRandomNumber()) % 200) + "/200/300")
                        .likeCount(0L) // TODO: CommentLike
                        .isLiked(false) // TODO: CommentLike
                        .build()).toList();

        return TypingTextFetchResponse.builder()
                .id(text.getId())
                .title(text.getTitle())
                .content(text.getContent())
                .authorId(text.getAuthorId())
                .likeCount(likeCount)
                .isLiked(isLiked)
                .isStarred(isStarred)
                .comments(comments)
                .build();
    }

    @Transactional(readOnly = true)
    public TypingTextFetchResponse getRandomText() {
        TypingText text = typingTextRepository.findAny()
                .orElseThrow(()-> new NotFoundException("text not found"));
        return TypingTextFetchResponse.builder()
                .title(text.getTitle())
                .content(text.getContent())
                .id(text.getId())
                .build();
    }
}
