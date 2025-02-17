package com.jonghwan.typing.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextCommentRepository extends JpaRepository<TextComment, Long> {
    List<TextComment> findByTypingTextId(Long textId);
}
