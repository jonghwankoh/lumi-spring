package com.jonghwan.typing.domain.typingtext.star;

import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.exception.custom.BadRequestException;
import com.jonghwan.typing.shared.exception.custom.NotFoundException;
import com.jonghwan.typing.shared.security.member.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TextStarService {
    private final TypingTextRepository textRepository;
    private final TextStarRepository starRepository;

    @Transactional
    public PostResponse addStar(LoginMember loginMember, Long textId) {
        if (!textRepository.existsById(textId)) {
            throw new NotFoundException("Text not found");
        }

        if(starRepository.existsByMemberIdAndTextId(loginMember.id(), textId)) {
            throw new BadRequestException("Star already exists");
        }

        TextStar star = TextStar.builder()
                .memberId(loginMember.id())
                .textId(textId)
                .build();
        starRepository.save(star);
        return PostResponse.of("Star saved", star.getId());
    }

    @Transactional
    public Response unstarText(LoginMember loginMember, Long textId) {
        TextStar star = starRepository.findByMemberIdAndTextId(loginMember.id(), textId)
                .orElseThrow(() -> new NotFoundException("Star not found"));
        starRepository.delete(star);
        return Response.of("Text deleted");
    }
}

