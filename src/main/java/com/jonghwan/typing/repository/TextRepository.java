package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TextRepository extends JpaRepository<Text, Long> {
    @Query(value = """
        SELECT * FROM TEXT
        ORDER BY RAND()
        LIMIT 1;
        """, nativeQuery = true)
    Optional<Text> findAny();
}
