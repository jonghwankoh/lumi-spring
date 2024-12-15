package com.jonghwan.typing.controller;

import com.jonghwan.typing.dto.WordDTO;
import com.jonghwan.typing.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;
    @GetMapping
    public Page<WordDTO> getWords(Pageable pageable) {
        return wordService.getWords(pageable);
    }
}
