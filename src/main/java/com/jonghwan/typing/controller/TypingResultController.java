package com.jonghwan.typing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import com.jonghwan.typing.dto.ResultFetchResponse;
import com.jonghwan.typing.dto.ResultSubmitRequest;
import com.jonghwan.typing.dto.PostResponse;
import com.jonghwan.typing.entity.TypingResult;
import com.jonghwan.typing.entity.TypingText;
import com.jonghwan.typing.entity.Member;
import com.jonghwan.typing.repository.TypingTextRepository;
import com.jonghwan.typing.repository.TypingResultRepository;
import com.jonghwan.typing.service.AuthService;
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

        return new PostResponse(true, "Typing result have been saved. id = " + typingResult.getId());
    }

    @GetMapping("/my")
    public ResultFetchResponse fetchMyTypingResult() throws JsonProcessingException {
        Member member = authService.getCurrentAuthenticatedUser();
        List<TypingResult> results = typingResultRepository.findByMemberId(member.getId());
        if(results.isEmpty()) {
            throw new RuntimeException("No typingResult exists");
        }

        return entityToResponse(results.get(0));
    }

    @GetMapping("/latest")
    public ResultFetchResponse fetchRecentTypingResult(@RequestParam Long textId) throws JsonProcessingException {
        Member member = authService.getCurrentAuthenticatedUser();
        List<TypingResult> results = typingResultRepository.findByMemberAndTypingTextOrdered(member.getId(), textId);
        if(results.isEmpty()) {
            throw new RuntimeException("No typingResult exists");
        }

        return entityToResponse(results.get(0));
    }

    @GetMapping("/{id}")
    public ResultFetchResponse fetchTypingResult(@PathVariable Long id) throws JsonProcessingException {
        TypingResult typingResult = typingResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No typingResult exists"));

        return entityToResponse(typingResult);
    }

    private ResultFetchResponse entityToResponse(TypingResult typingResult) throws JsonProcessingException {
        ResultFetchResponse response = new ResultFetchResponse();
        response.setTextId(typingResult.getTypingText().getId());
        response.setAccuracy(typingResult.getAccuracy());
        response.setActualAccuracy(typingResult.getActualAccuracy());
        response.setElapsedMs(typingResult.getElapsedMs());
        response.setCpm(typingResult.getCpm());

        // TODO: 어차피 json으로 보낼텐데 굳이 리스트로 만들어야할까? 불필요한 연산 정리가 가능한지는 추후에 결정하도록 하자.
        // TODO: 조회: String(DB) -> list(DTO) -> json(client)
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType booleanListType = objectMapper.getTypeFactory().constructCollectionType(List.class, Boolean.class);
        CollectionType integerListType = objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class);
        List<Integer> elapsedTimePerLetter = objectMapper.readValue(typingResult.getElapsedMsPerLetter(), integerListType);
        List<Boolean> matchPerLetter = objectMapper.readValue(typingResult.getMatchPerLetter(), booleanListType);
        response.setElapsedMsPerLetter(elapsedTimePerLetter);
        response.setMatchPerLetter(matchPerLetter);

        return response;
    }

    private TypingResult requestToEntity(ResultSubmitRequest request, Member member) throws JsonProcessingException {
        TypingText typingText = typingTextRepository.findById(request.getTextId())
                .orElseThrow(() -> new RuntimeException("No such TypingText id"));

        TypingResult typingResult = new TypingResult();
        typingResult.setMember(member);
        typingResult.setTypingText(typingText);

        typingResult.setAccuracy(request.getAccuracy());
        typingResult.setActualAccuracy(request.getActualAccuracy());
        typingResult.setElapsedMs(request.getElapsedMs());
        typingResult.setCpm(request.getCpm());

        // TODO: 저장: json(client) -> list(JPA) -> String(DB)
        List<Boolean> matchPerLetter = request.getMatchPerLetter();
        List<Integer> elapsedMsPerLetter = request.getElapsedMsPerLetter();
        ObjectMapper objectMapper = new ObjectMapper();
        typingResult.setMatchPerLetter(objectMapper.writeValueAsString(matchPerLetter));
        typingResult.setElapsedMsPerLetter(objectMapper.writeValueAsString(elapsedMsPerLetter));

        return typingResult;
    }
}
