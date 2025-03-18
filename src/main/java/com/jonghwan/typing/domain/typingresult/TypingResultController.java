package com.jonghwan.typing.domain.typingresult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import com.jonghwan.typing.domain.typingresult.dto.ResultFetchResponse;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.domain.typingresult.dto.ResultSubmitRequest;
import com.jonghwan.typing.shared.base.dto.PostResponse;
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
    public PostResponse postTypingResult(@RequestBody ResultSubmitRequest request) throws JsonProcessingException {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingResult typingResult = requestToEntity(request, member);
        typingResultRepository.save(typingResult);

        return new PostResponse("Typing result have been saved", typingResult.getId());
    }

    @GetMapping("/my")
    public List<ResultFetchResponse> fetchMyTypingResults(@RequestParam(defaultValue = "5") int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }

        Member member = authService.getCurrentAuthenticatedUser();
        List<TypingResult> results = typingResultRepository.findByMemberIdOrderByCreatedAtDesc(member.getId());

        return results.stream()
                .limit(limit)
                .map(result -> {
                    try {
                        return entityToResponse(result);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to process JSON", e);
                    }
                })
                .toList();
    }

    @GetMapping("/{id}")
    public ResultFetchResponse fetchTypingResultById(@PathVariable Long id) throws JsonProcessingException {
        TypingResult typingResult = typingResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No typingResult exists"));

        return entityToResponse(typingResult);
    }

    private ResultFetchResponse entityToResponse(TypingResult typingResult) throws JsonProcessingException {
        ResultFetchResponse response = new ResultFetchResponse();
        response.setId(typingResult.getId());
        response.setTextId(typingResult.getTypingText().getId());
        response.setTitle(typingResult.getTypingText().getTitle());
        response.setAccuracy(typingResult.getAccuracy());
        response.setActualAccuracy(typingResult.getActualAccuracy());
        response.setElapsedMs(typingResult.getElapsedMs());
        response.setCpm(typingResult.getCpm());
        response.setCreatedAt(typingResult.getCreatedAt());

        // TODO: 어차피 json으로 보낼텐데 굳이 리스트로 만들어야할까? 불필요한 연산 정리가 가능한지는 추후에 결정하도록 하자.
        // TODO: 조회: String(DB) -> list(DTO) -> json(client)
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType booleanListType = objectMapper.getTypeFactory().constructCollectionType(List.class, Boolean.class);
        CollectionType integerListType = objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class);
        List<Integer> elapsedTimePerLetter = objectMapper.readValue(typingResult.getElapsedMsPerChar(), integerListType);
        List<Boolean> matchPerChar = objectMapper.readValue(typingResult.getMatchPerChar(), booleanListType);
        response.setElapsedMsPerChar(elapsedTimePerLetter);
        response.setMatchPerChar(matchPerChar);

        return response;
    }

    private TypingResult requestToEntity(ResultSubmitRequest request, Member member) throws JsonProcessingException {
        TypingText typingText = typingTextRepository.findById(request.getTextId())
                .orElseThrow(() -> new RuntimeException("No such TypingText id"));

        // TODO: 불필요한 변환 문제 json(client) -> list(JPA) -> String(DB)
        List<Boolean> matchPerChar = request.getMatchPerChar();
        List<Integer> elapsedMsPerChar = request.getElapsedMsPerChar();
        ObjectMapper objectMapper = new ObjectMapper();

        return TypingResult.builder()
                .member(member)
                .typingText(typingText)
                .accuracy(request.getAccuracy())
                .actualAccuracy(request.getActualAccuracy())
                .matchPerChar(objectMapper.writeValueAsString(matchPerChar))
                .elapsedMsPerChar(objectMapper.writeValueAsString(elapsedMsPerChar))
                .elapsedMs(request.getElapsedMs())
                .cpm(request.getCpm())
                .build();
    }
}
