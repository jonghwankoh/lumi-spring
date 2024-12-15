package com.jonghwan.typing.dto;

import lombok.Data;

@Data
public class WordDTO {
    private Long id;
    private String word;
    private String meaning;

    public WordDTO(Long id, String word, String meaning) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
    }
}
