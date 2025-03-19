package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.shared.security.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TextLikeRepository extends JpaRepository<TextLike, Long> {
    @Query("SELECT COUNT(l) FROM TextLike l WHERE l.typingText.id = :textId")
    Long countByTypingTextId(Long textId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
    "FROM TextLike l WHERE l.typingText.id = :textId AND l.member.id = :memberId")
    boolean existsByTypingTextIdAndMemberId(Long textId, Long memberId);

    @Query("SELECT l FROM TextLike l WHERE l.member = :member AND l.typingText.id = :textId")
    Optional<TextLike> findByMemberAndTypingTextId(Member member, Long textId);
}
