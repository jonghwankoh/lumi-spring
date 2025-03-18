package com.jonghwan.typing.domain.vocab;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vocab extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vocabText;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public Vocab(String vocabText, Member author) {
        this.vocabText = vocabText;
        this.author = author;
    }
}