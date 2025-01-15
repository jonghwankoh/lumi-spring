package com.jonghwan.typing.entity.sentence;

import com.jonghwan.typing.entity.BaseEntity;
import com.jonghwan.typing.entity.TypingText;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Sentence extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "text_id", nullable = false)
    private TypingText typingText;

    @Column(nullable = false)
    private String sentenceText;


    @Column(nullable = false)
    private String translation;
}