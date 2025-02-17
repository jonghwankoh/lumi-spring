package com.jonghwan.typing.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TextCommentFetchResponse {
    private Long id;

    private Long authorId;
    private String authorName;
    private String authorImg;

    private String content;

    private Integer likeNum;
    private Boolean isLiked;


    private LocalDateTime createdAt;
}
