package com.jonghwan.typing.domain.vocab;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocabMeaning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="vocab_id", nullable = false)
    private Vocab vocab;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public VocabMeaning(Vocab vocab, String description, Member author) {
        this.vocab = vocab;
        this.description = description;
        this.author = author;
    }
}