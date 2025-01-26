package com.jonghwan.typing.domain.typingtext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TypingTextRepository extends JpaRepository<TypingText, Long> {
    @Query(value = """
        SELECT * FROM TYPING_TEXT
        ORDER BY RAND()
        LIMIT 1;
        """, nativeQuery = true)
    Optional<TypingText> findAny();
}
