package com.jonghwan.typing.domain.typingtext.star;

import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.base.exception.BadRequestException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TextStarService {
    private final AuthService authService;
    private final TypingTextRepository textRepository;
    private final TextStarRepository starRepository;

    @Transactional
    public PostResponse addStar(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingText typingText = textRepository.findById(textId)
                .orElseThrow(() -> new NotFoundException("Text not found"));

        boolean exists = starRepository.existsByTypingTextIdAndMemberId(textId, member.getId());
        if(exists) {
            throw new BadRequestException("Star already exists");
        }

        TextStar star = TextStar.builder()
                .member(member)
                .typingText(typingText)
                .build();
        starRepository.save(star);
        return PostResponse.of("Star saved", star.getId());
    }

    @Transactional
    public Response unstarText(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();

        TextStar star = starRepository.findByMemberAndTypingTextId(member, textId)
                .orElseThrow(() -> new NotFoundException("Star not found"));
        starRepository.delete(star);
        return Response.of("Text deleted");
    }
}

