package com.jonghwan.typing.controller;

import com.jonghwan.typing.entity.Paragraph;
import com.jonghwan.typing.repository.ParagraphRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParagraphController {
    private final ParagraphRepository repository;

    @GetMapping("/paragraph/random")
    public Paragraph getRandomParagraph(@RequestParam Long nonce) {
        log.info("repository.findByNonce(nonce)");
        return repository.findByNonce(nonce);
    }
}
