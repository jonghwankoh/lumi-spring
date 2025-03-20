package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.domain.comment.dto.TextCommentFetchResponse;
import com.jonghwan.typing.domain.comment.dto.TextCommentSubmitRequest;
import com.jonghwan.typing.domain.typingtext.TypingTextRepository;
import com.jonghwan.typing.shared.base.dto.PostResponse;
import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.exception.custom.ForbiddenException;
import com.jonghwan.typing.shared.exception.custom.NotFoundException;
import com.jonghwan.typing.shared.constant.RandomNumberProvider;
import com.jonghwan.typing.shared.security.member.LoginMember;
import com.jonghwan.typing.shared.security.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextCommentService {
    private final MemberRepository memberRepository;
    private final TextCommentRepository repository;
    private final TypingTextRepository textRepository;
    private final RandomNumberProvider randomNumberProvider;

    @Transactional(readOnly = true)
    public List<TextCommentFetchResponse> getCommentByTextId(Long textId) {
        return repository.findByTypingTextId(textId).stream().map(this::entityToResponse)
                .toList();
    }

    @Transactional
    public PostResponse postTextComment(LoginMember loginMember, Long textId, TextCommentSubmitRequest request) {
        if(!textRepository.existsById(textId)){
            throw new NotFoundException("text not found");
        }
        TextComment textComment = TextComment.builder()
                .textId(textId)
                .memberId(loginMember.id())
                .content(request.content())
                .build();
        repository.save(textComment);

        return PostResponse.of("Comment has been saved", textComment.getId());
    }

    @Transactional
    public Response deleteTextComment(LoginMember loginMember, Long commentId) {
        TextComment textComment = repository.findById(commentId).orElse(null);
        if (textComment == null) {
            throw new NotFoundException("Comment not found.");
        }
        if (!Objects.equals(textComment.getMemberId(), loginMember.id())) {
            throw new ForbiddenException("You are not authorized to delete this comment.");
        }

        repository.delete(textComment);
        return Response.of("Comment has been deleted.");
    }

    private TextCommentFetchResponse entityToResponse(TextComment textComment) {
        return TextCommentFetchResponse.builder()
                .id(textComment.getId())
                .likeCount(0L) // TODO: TextCommentLike
                .isLiked(false) // TODO: TextCommentLike
                // TODO: author Image
                .authorImg("https://picsum.photos/id/" + ((textComment.getMemberId() + randomNumberProvider.getRandomNumber()) % 200) + "/200/300")
                .authorId(textComment.getMemberId() )
                .authorName(textComment.getMember().getName())
                .content(textComment.getContent())
                .createdAt(textComment.getCreatedAt())
                .build();
    }
}
