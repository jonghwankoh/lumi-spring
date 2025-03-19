package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.exception.custom.BadRequestException;
import com.jonghwan.typing.shared.exception.custom.NotFoundException;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TextLikeService {
    private final AuthService authService;
    private final TypingTextRepository textRepository;
    private final TextLikeRepository likeRepository;

    @Transactional
    public PostResponse addLike(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingText typingText = textRepository.findById(textId)
                .orElseThrow(() -> new NotFoundException("Text not found"));

        boolean exists = likeRepository.existsByTypingTextIdAndMemberId(textId, member.getId());
        if(exists) {
            throw new BadRequestException("Like already exists");
        }

        TextLike like = TextLike.builder()
                .member(member)
                .typingText(typingText)
                .build();
        likeRepository.save(like);

        return PostResponse.of("Like saved", like.getId());
    }

    @Transactional
    public Response unlikeText(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();

        TextLike textLike = likeRepository.findByMemberAndTypingTextId(member, textId)
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(textLike);

        return Response.of("Text deleted");
    }
}

