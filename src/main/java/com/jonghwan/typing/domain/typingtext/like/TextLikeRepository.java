package com.jonghwan.typing.domain.typingtext.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextLikeRepository extends JpaRepository<TextLike, Long> {
    Long countByTextId(Long textId);
    boolean existsByMemberIdAndTextId(Long memberId, Long textId);
    Optional<TextLike> findByMemberIdAndTextId(Long memberId, Long textId);
}
