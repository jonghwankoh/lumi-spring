package com.jonghwan.typing.domain.typingtext.star;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextStarRepository extends JpaRepository<TextStar, Long> {
    boolean existsByMemberIdAndTextId(Long memberId, Long textId);
    Optional<TextStar> findByMemberIdAndTextId(Long memberId, Long textId);
}
