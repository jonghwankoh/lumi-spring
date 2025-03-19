package com.jonghwan.typing.domain.typingresult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonghwan.typing.domain.typingresult.dto.ResultFetchResponse;
import com.jonghwan.typing.domain.typingresult.dto.ResultSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.exception.BadRequestException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypingResultService {
    private final AuthService authService;
    private final TypingTextRepository typingTextRepository;
    private final TypingResultRepository typingResultRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public PostResponse saveTypingResult(ResultSubmitRequest request) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingResult typingResult = requestToEntity(request, member);
        typingResultRepository.save(typingResult);
        return PostResponse.of("Typing result has been saved", typingResult.getId());
    }

    @Transactional(readOnly = true)
    public List<ResultFetchResponse> fetchMyTypingResults(int limit) {
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

    @Transactional(readOnly = true)
    public ResultFetchResponse fetchTypingResultById(Long id) {
        TypingResult typingResult = typingResultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No typingResult exists"));

        return entityToResponse(typingResult);
    }

    private ResultFetchResponse entityToResponse(TypingResult typingResult) {
        try {
            List<Boolean> matchPerChar = objectMapper.readValue(typingResult.getMatchPerCharJson(), new TypeReference<>() {});
            List<Integer> elapsedMsPerChar = objectMapper.readValue(typingResult.getElapsedMsPerCharJson(), new TypeReference<>() {});

            return ResultFetchResponse.builder()
                    .id(typingResult.getId())
                    .textId(typingResult.getTypingText().getId())
                    .title(typingResult.getTypingText().getTitle())
                    .accuracy(typingResult.getAccuracy())
                    .actualAccuracy(typingResult.getActualAccuracy())
                    .elapsedMs(typingResult.getElapsedMs())
                    .cpm(typingResult.getCpm())
                    .createdAt(typingResult.getCreatedAt())
                    .matchPerChar(matchPerChar)
                    .elapsedMsPerChar(elapsedMsPerChar)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format in DB", e);
        }
    }

    private TypingResult requestToEntity(ResultSubmitRequest request, Member member) {
        TypingText typingText = typingTextRepository.findById(request.textId())
                .orElseThrow(() -> new NotFoundException("No such TypingText id"));
        try {
            String matchPerCharJson = objectMapper.writeValueAsString(request.matchPerChar());
            String elapsedMsPerCharJson = objectMapper.writeValueAsString(request.elapsedMsPerChar());
            return TypingResult.builder()
                    .member(member)
                    .typingText(typingText)
                    .accuracy(request.accuracy())
                    .actualAccuracy(request.actualAccuracy())
                    .matchPerCharJson(matchPerCharJson)
                    .elapsedMsPerCharJson(elapsedMsPerCharJson)
                    .elapsedMs(request.elapsedMs())
                    .cpm(request.cpm())
                    .build();
        } catch (JsonProcessingException e) {
            log.warn("Json processing exception: {}", e.getMessage(), e);
            throw new BadRequestException("Invalid JSON list request");
        }
    }
}