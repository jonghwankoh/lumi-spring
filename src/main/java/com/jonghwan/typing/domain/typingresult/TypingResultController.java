package com.jonghwan.typing.domain.typingresult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonghwan.typing.domain.typingresult.dto.ResultFetchResponse;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.domain.typingresult.dto.ResultSubmitRequest;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.exception.BadRequestException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.shared.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class TypingResultController {
    private final TypingResultService typingResultService;

    @PostMapping
    public PostResponse postTypingResult(@RequestBody ResultSubmitRequest request) {
        return typingResultService.saveTypingResult(request);
    }

    @GetMapping("/my")
    public List<ResultFetchResponse> fetchMyTypingResults(@RequestParam(defaultValue = "5") int limit) {
        return typingResultService.fetchMyTypingResults(limit);
    }

    @GetMapping("/{id}")
    public ResultFetchResponse fetchTypingResultById(@PathVariable Long id) {
        return typingResultService.fetchTypingResultById(id);
    }
}