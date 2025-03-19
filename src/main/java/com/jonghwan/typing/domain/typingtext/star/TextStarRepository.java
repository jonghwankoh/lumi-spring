package com.jonghwan.typing.domain.typingtext.star;

import com.jonghwan.typing.shared.security.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TextStarRepository extends JpaRepository<TextStar, Long> {
    @Query("SELECT COUNT(s) FROM TextStar s WHERE s.typingText.id = :textId")
    Long countByTypingTextId(Long textId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
    "FROM TextStar s WHERE s.typingText.id = :textId AND s.member.id = :memberId")
    boolean existsByTypingTextIdAndMemberId(Long textId, Long memberId);

    @Query("SELECT l FROM TextStar l WHERE l.member = :member AND l.typingText.id = :textId")
    Optional<TextStar> findByMemberAndTypingTextId(Member member, Long textId);
}
