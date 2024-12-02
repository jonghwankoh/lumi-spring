package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
    @Query(value = """
        SELECT * FROM PARAGRAPH
        ORDER BY RAND()
        LIMIT 1;
        """, nativeQuery = true)
    Optional<Paragraph> findAny();
}
