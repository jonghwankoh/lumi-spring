package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Paragraph;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class ParagraphRepositoryTest {
    private Paragraph sample1;
    private Paragraph sample2;

    @Autowired
    private ParagraphRepository paragraphRepository;

    @BeforeEach
    void setUp() {
        sample1 = new Paragraph();
        sample1.setTitle("Sample Title");
        sample1.setContent("Sample Content");

        sample2 = new Paragraph();
        sample2.setTitle("Another Title");
        sample2.setContent("Another Content");

        paragraphRepository.save(sample1);
        paragraphRepository.save(sample2);
    }


    @Test
    void getRandom() {
        for (int i = 0; i < 5; i++) {
            Paragraph findParagraph = paragraphRepository.findAny().orElseThrow();

            log.info("id = {}", findParagraph.getId());
            log.info("title = {}", findParagraph.getTitle());

            assertThat(findParagraph).isIn(sample1, sample2);
        }
    }

    @Test
    void getById() {
        Paragraph find1 = paragraphRepository.findById(sample1.getId()).orElseThrow();

        assertThat(find1.getTitle()).isEqualTo(sample1.getTitle());
        assertThat(find1.getContent()).isEqualTo(sample1.getContent());

        Paragraph find2 = paragraphRepository.findById(sample2.getId()).orElseThrow();

        assertThat(find2.getTitle()).isEqualTo(sample2.getTitle());
        assertThat(find2.getContent()).isEqualTo(sample2.getContent());
    }
}