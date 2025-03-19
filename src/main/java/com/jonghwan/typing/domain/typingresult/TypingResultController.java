package com.jonghwan.typing.domain.typingresult;

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
    private final AuthService authService;
    private final TypingTextRepository typingTextRepository;
    private final TypingResultRepository typingResultRepository;

    @PostMapping
    public PostResponse postTypingResult(@RequestBody ResultSubmitRequest request) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingResult typingResult = requestToEntity(request, member);
        typingResultRepository.save(typingResult);

        return PostResponse.of("Typing result have been saved", typingResult.getId());
    }

    @GetMapping("/my")
    public List<ResultFetchResponse> fetchMyTypingResults(@RequestParam(defaultValue = "5") int limit) {
        if (limit <= 0) {
            throw new BadRequestException("Limit must be greater than 0");
        }

        Member member = authService.getCurrentAuthenticatedUser();
        List<TypingResult> results = typingResultRepository.findByMemberIdOrderByCreatedAtDesc(member.getId());

        return results.stream()
                .limit(limit)
                .map(this::entityToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResultFetchResponse fetchTypingResultById(@PathVariable Long id) {
        TypingResult typingResult = typingResultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No typingResult exists"));

        return entityToResponse(typingResult);
    }

    private ResultFetchResponse entityToResponse(TypingResult typingResult) {
        return ResultFetchResponse.builder()
                .id(typingResult.getId())
                .textId(typingResult.getTypingText().getId())
                .title(typingResult.getTypingText().getTitle())
                .accuracy(typingResult.getAccuracy())
                .actualAccuracy(typingResult.getActualAccuracy())
                .elapsedMs(typingResult.getElapsedMs())
                .cpm(typingResult.getCpm())
                .createdAt(typingResult.getCreatedAt())
                .elapsedMsPerChar(typingResult.getElapsedMsPerChar())
                .matchPerChar(typingResult.getMatchPerChar())
                .build();
    }

    private TypingResult requestToEntity(ResultSubmitRequest request, Member member) {
        TypingText typingText = typingTextRepository.findById(request.textId())
                .orElseThrow(() -> new RuntimeException("No such TypingText id"));
        return TypingResult.builder()
                .member(member)
                .typingText(typingText)
                .accuracy(request.accuracy())
                .actualAccuracy(request.actualAccuracy())
                .matchPerChar(request.matchPerChar())
                .elapsedMsPerChar(request.elapsedMsPerChar())
                .elapsedMs(request.elapsedMs())
                .cpm(request.cpm())
                .build();
    }
}
