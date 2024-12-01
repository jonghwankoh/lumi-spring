package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
    @Query(value = """
        SELECT * FROM PARAGRAPH
        WHERE id = MOD(:nonce, (SELECT COUNT(*) FROM PARAGRAPH)) + 1
        """, nativeQuery = true)
    Paragraph findByNonce(@Param("nonce") long nonce);
}
