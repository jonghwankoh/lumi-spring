package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Text;
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
class TextRepositoryTest {
    private Text sample1;
    private Text sample2;

    @Autowired
    private TextRepository textRepository;

    @BeforeEach
    void setUp() {
        sample1 = new Text();
        sample1.setTitle("Sample Title");
        sample1.setContent("Sample Content");

        sample2 = new Text();
        sample2.setTitle("Another Title");
        sample2.setContent("Another Content");

        textRepository.save(sample1);
        textRepository.save(sample2);
    }


    @Test
    void getRandom() {
        for (int i = 0; i < 5; i++) {
            Text findText = textRepository.findAny().orElseThrow();

            log.info("id = {}", findText.getId());
            log.info("title = {}", findText.getTitle());

            assertThat(findText).isIn(sample1, sample2);
        }
    }

    @Test
    void getById() {
        Text find1 = textRepository.findById(sample1.getId()).orElseThrow();

        assertThat(find1.getTitle()).isEqualTo(sample1.getTitle());
        assertThat(find1.getContent()).isEqualTo(sample1.getContent());

        Text find2 = textRepository.findById(sample2.getId()).orElseThrow();

        assertThat(find2.getTitle()).isEqualTo(sample2.getTitle());
        assertThat(find2.getContent()).isEqualTo(sample2.getContent());
    }
}