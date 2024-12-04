package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.TypingText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TextRepository extends JpaRepository<TypingText, Long> {
    @Query(value = """
        SELECT * FROM TYPING_TEXT
        ORDER BY RAND()
        LIMIT 1;
        """, nativeQuery = true)
    Optional<TypingText> findAny();
}
