package com.jonghwan.typing.domain.sentence;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.domain.typingtext.TypingText;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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