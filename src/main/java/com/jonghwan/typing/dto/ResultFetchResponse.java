package com.jonghwan.typing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultFetchResponse {
    private Long textId;
    private int accuracy;
    private int actualAccuracy;
    private int elapsedMs;
    private int cpm;

    private List<Boolean> matchPerLetter;
    private List<Integer> elapsedMsPerLetter;
}
