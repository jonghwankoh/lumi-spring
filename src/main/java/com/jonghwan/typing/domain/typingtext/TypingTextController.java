package com.jonghwan.typing.domain.typingtext;

import com.jonghwan.typing.domain.typingtext.dto.TypingTextFetchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TypingTextController {
    private final TypingTextService textService;

    @GetMapping("/text/{id}")
    public TypingTextFetchResponse getTextById(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean fullDetails) {
        return textService.getTypingText(id, fullDetails);
    }

    @GetMapping("/text/random")
    public TypingTextFetchResponse getRandomText() {
        return textService.getRandomText();
    }
}
