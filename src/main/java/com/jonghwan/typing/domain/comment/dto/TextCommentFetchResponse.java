package com.jonghwan.typing.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TextCommentFetchResponse {
    private Long id;

    private Long authorId;
    private String authorName;
    private String authorImg;

    private String content;

    private Long likeCount;
    private Boolean isLiked;


    private LocalDateTime createdAt;
}
