package com.jonghwan.typing.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record TextCommentSubmitRequest(String content) {
}
