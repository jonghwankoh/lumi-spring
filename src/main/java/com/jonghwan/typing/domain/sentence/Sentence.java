package com.jonghwan.typing.domain.sentence;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.domain.typingtext.TypingText;
import com.jonghwan.typing.shared.security.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sentence extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "text_id", nullable = false)
    private TypingText typingText;

    @Column(nullable = false)
    private String sentenceText;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public Sentence(TypingText typingText, String sentenceText, Member author) {
        this.typingText = typingText;
        this.sentenceText = sentenceText;
        this.author = author;
    }
}