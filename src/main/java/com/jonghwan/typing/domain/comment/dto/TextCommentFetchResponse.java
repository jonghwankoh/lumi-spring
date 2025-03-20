package com.jonghwan.typing.domain.comment.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TextCommentFetchResponse(
        Long id,
        Long authorId,
        String authorName,
        String authorImg,
        String content,
        Long likeCount,
        Boolean isLiked,
        LocalDateTime createdAt
) {
}
