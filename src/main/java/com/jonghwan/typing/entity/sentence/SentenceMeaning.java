package com.jonghwan.typing.entity.sentence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SentenceMeaning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sentence;

    @Column(nullable = false)
    private String translation;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}