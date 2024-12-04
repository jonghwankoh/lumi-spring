package com.jonghwan.typing.controller;

import com.jonghwan.typing.entity.Text;
import com.jonghwan.typing.repository.TextRepository;
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
    private final TextRepository repository;

    @GetMapping("/text/{id}")
    public Optional<Text> getTextById(@PathVariable Long id) {
        log.info("repository.findById(id)");
        return repository.findById(id);
    }

    @GetMapping("/text/random")
    public Optional<Text> getRandomText() {
        log.info("repository.findAny()");
        return repository.findAny();
    }
}
