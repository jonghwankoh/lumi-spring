package com.jonghwan.typing.service;

import com.jonghwan.typing.dto.WordDTO;
import com.jonghwan.typing.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public Page<WordDTO> getWords(Pageable pageable) {
        return wordRepository.findAll(pageable).map(word -> new WordDTO(word.getId(), word.getWord(), word.getMeaning()));
    }
}
