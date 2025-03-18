package com.jonghwan.typing.domain.sentence;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentenceMeaning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sentence_id")
    private Sentence sentence;

    @Column(nullable = false)
    private String translation;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public SentenceMeaning(Sentence sentence, String translation, Member author) {
        this.sentence = sentence;
        this.translation = translation;
        this.author = author;
    }
}