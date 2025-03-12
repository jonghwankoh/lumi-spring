package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.DeleteResponse;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.exception.BadRequestException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.security.AuthService;
import com.jonghwan.typing.shared.security.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TextLikeService {
    private final AuthService authService;
    private final TypingTextRepository textRepository;
    private final TextLikeRepository likeRepository;

    public PostResponse addLike(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();
        TypingText typingText = textRepository.findById(textId)
                .orElseThrow(() -> new NotFoundException("Text not found"));

        boolean exists = likeRepository.existsByTypingTextIdAndMemberId(textId, member.getId());
        if(exists) {
            throw new BadRequestException("Like already exists");
        }

        TextLike like = new TextLike(member, typingText);
        likeRepository.save(like);

        return new PostResponse(true, "Like saved", like.getId());
    }

    public DeleteResponse unlikeText(Long textId) {
        Member member = authService.getCurrentAuthenticatedUser();

        TextLike textLike = likeRepository.findByMemberAndTypingTextId(member, textId)
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(textLike);

        return new DeleteResponse(true, "Text deleted");
    }
}

