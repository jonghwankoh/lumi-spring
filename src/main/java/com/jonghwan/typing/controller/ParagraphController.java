package com.jonghwan.typing.controller;

import com.jonghwan.typing.entity.Paragraph;
import com.jonghwan.typing.repository.ParagraphRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParagraphController {
    private final ParagraphRepository repository;

    @GetMapping("/paragraph/{id}")
    public Optional<Paragraph> getParagraphById(@PathVariable Long id) {
        log.info("repository.findById(id)");
        return repository.findById(id);
    }

    @GetMapping("/paragraph/random")
    public Optional<Paragraph> getRandomParagraph() {
        log.info("repository.findAny()");
        return repository.findAny();
    }
}
