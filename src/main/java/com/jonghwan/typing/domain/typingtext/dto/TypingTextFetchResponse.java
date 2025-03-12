package com.jonghwan.typing.domain.typingtext.dto;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TypingTextFetchResponse {
    private Long id;
    private String title;
    private String content;

    private Long authorId;
    private List<TextCommentFetchResponse> comments;
    private Long likeCount;
    private Boolean isLiked;
    private Boolean isStarred;
}
