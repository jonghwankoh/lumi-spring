package com.jonghwan.typing.domain.typingresult;

import com.jonghwan.typing.domain.typingresult.dto.ResultFetchResponse;
import com.jonghwan.typing.domain.typingresult.dto.ResultSubmitRequest;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.security.member.Login;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
public class TypingResultController {
    private final TypingResultService typingResultService;

    @PostMapping
    public PostResponse postTypingResult(@Login LoginMember loginMember, @RequestBody ResultSubmitRequest request) {
        return typingResultService.saveTypingResult(loginMember, request);
    }

    @GetMapping("/my")
    public List<ResultFetchResponse> fetchMyTypingResults(@Login LoginMember loginMember, @RequestParam(defaultValue = "5") int limit) {
        return typingResultService.fetchMyTypingResults(loginMember, limit);
    }

    @GetMapping("/{id}")
    public ResultFetchResponse fetchTypingResultById(@PathVariable Long id) {
        return typingResultService.fetchTypingResultById(id);
    }
}