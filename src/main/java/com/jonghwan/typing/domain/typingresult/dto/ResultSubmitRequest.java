package com.jonghwan.typing.domain.typingresult.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public record ResultSubmitRequest(
        Long textId,
        Integer accuracy,
        Integer actualAccuracy,
        Integer elapsedMs,
        Integer cpm,

        String matchPerChar,
        String elapsedMsPerChar
) {
}
