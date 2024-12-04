package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.TypingText;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class TypingTextRepositoryTest {
    private TypingText sample1;
    private TypingText sample2;

    @Autowired
    private TextRepository textRepository;

    @BeforeEach
    void setUp() {
        sample1 = new TypingText();
        sample1.setTitle("Sample Title");
        sample1.setContent("Sample Content");

        sample2 = new TypingText();
        sample2.setTitle("Another Title");
        sample2.setContent("Another Content");

        textRepository.save(sample1);
        textRepository.save(sample2);
    }


    @Test
    void getRandom() {
        for (int i = 0; i < 5; i++) {
            TypingText findTypingText = textRepository.findAny().orElseThrow();

            log.info("id = {}", findTypingText.getId());
            log.info("title = {}", findTypingText.getTitle());

            assertThat(findTypingText).isIn(sample1, sample2);
        }
    }

    @Test
    void getById() {
        TypingText find1 = textRepository.findById(sample1.getId()).orElseThrow();

        assertThat(find1.getTitle()).isEqualTo(sample1.getTitle());
        assertThat(find1.getContent()).isEqualTo(sample1.getContent());

        TypingText find2 = textRepository.findById(sample2.getId()).orElseThrow();

        assertThat(find2.getTitle()).isEqualTo(sample2.getTitle());
        assertThat(find2.getContent()).isEqualTo(sample2.getContent());
    }
}