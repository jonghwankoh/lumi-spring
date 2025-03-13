package com.jonghwan.typing.domain.typingresult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypingResultRepository extends JpaRepository<TypingResult, Long> {
    @Query("select r from TypingResult r where r.member.id = :memberId and r.typingText.id = :textId order by r.createdAt desc")
    List<TypingResult> findByMemberAndTypingTextOrdered(@Param("memberId") Long memberId, @Param("textId") Long textId);

    List<TypingResult> findByMemberIdOrderByCreatedAtDesc(Long memberId);
}
