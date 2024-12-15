package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
    Page<Word> findAll(Pageable pageable);
}
