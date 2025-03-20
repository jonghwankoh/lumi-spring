package com.jonghwan.typing.domain.typingtext.like;

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
public class TextLikeService {
    private final TypingTextRepository textRepository;
    private final TextLikeRepository likeRepository;

    @Transactional
    public PostResponse addLike(LoginMember loginMember, Long textId) {
        if(!textRepository.existsById(textId)){
            throw new NotFoundException("Text not found");
        }

        if(likeRepository.existsByMemberIdAndTextId(loginMember.id(), textId)) {
            throw new BadRequestException("Like already exists");
        }

        TextLike like = TextLike.builder()
                .memberId(loginMember.id())
                .textId(textId)
                .build();
        likeRepository.save(like);

        return PostResponse.of("Like saved", like.getId());
    }

    @Transactional
    public Response unlikeText(LoginMember loginMember, Long textId) {
        TextLike textLike = likeRepository.findByMemberIdAndTextId(loginMember.id(), textId)
                .orElseThrow(() -> new NotFoundException("Like not found"));
        likeRepository.delete(textLike);
        return Response.of("Text deleted");
    }
}

