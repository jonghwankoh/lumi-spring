package com.jonghwan.typing.domain.typingresult.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ResultSubmitRequest(
        Long textId,
        Integer accuracy,
        Integer actualAccuracy,
        Integer elapsedMs,
        Integer cpm,

        List<Boolean> matchPerChar,
        List<Integer> elapsedMsPerChar
) {
}
