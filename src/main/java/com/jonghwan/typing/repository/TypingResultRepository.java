package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.TypingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypingResultRepository extends JpaRepository<TypingResult, Long> {
    @Query("select r from TypingResult r where r.member.id = :memberId and r.typingText.id = :textId order by r.createdAt desc")
    List<TypingResult> findByUserAndTypingTextOrdered(@Param("memberId") Long userId, @Param("textId") Long textId);

    //TODO: findTopByUserIdAndResourceBIdOrderByCreatedAtDesc 검토(chat gpt 피셜)
    //Optional<TypingResult> findTopByUserIdAndTextIdOrderByCreatedAtDesc(Long userId, Long textId);
}
