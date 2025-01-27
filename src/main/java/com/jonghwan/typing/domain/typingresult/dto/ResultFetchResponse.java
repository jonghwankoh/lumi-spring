package com.jonghwan.typing.domain.typingresult.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultFetchResponse {
    private Long id;
    private Long textId;
    private String title;
    private int accuracy;
    private int actualAccuracy;
    private int elapsedMs;
    private int cpm;
    private LocalDateTime createdAt;

    private List<Boolean> matchPerChar;
    private List<Integer> elapsedMsPerChar;
}
