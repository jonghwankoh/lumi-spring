package com.jonghwan.typing.domain.typingresult.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResultFetchResponse(
    Long id,
    Long textId,
    String title,
    int accuracy,
    int actualAccuracy,
    int elapsedMs,
    int cpm,
    LocalDateTime createdAt,
    String matchPerChar,
    String elapsedMsPerChar
) { }
