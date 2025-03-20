package com.jonghwan.typing.domain.typingtext.dto;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record TypingTextFetchResponse(
        Long id,
        String title,
        String content,

        Long authorId,
        List<TextCommentFetchResponse> comments,
        Long likeCount,
        Boolean isLiked,
        Boolean isStarred
) {
}
