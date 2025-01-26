package com.jonghwan.typing.domain.typingtext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextController {
    private final TypingTextRepository repository;

    @GetMapping("/text/{id}")
    public Optional<TypingText> getTextById(@PathVariable Long id) {
        log.info("repository.findById(id)");
        return repository.findById(id);
    }

    @GetMapping("/text/random")
    public Optional<TypingText> getRandomText() {
        log.info("repository.findAny()");
        return repository.findAny();
    }
}
