package com.jonghwan.typing.domain.typingresult;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.domain.typingtext.TypingText;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypingResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "text_id", nullable = false)
    private TypingText typingText;

    @Column(nullable = false)
    private int accuracy;

    @Column(nullable = false)
    private int actualAccuracy;

    @Column(nullable = false)
    private int elapsedMs;

    @Column(nullable = false)
    private int cpm;

    @Column(columnDefinition = "json", nullable = false)
    private String matchPerChar;

    @Column(columnDefinition = "json", nullable = false)
    private String elapsedMsPerChar;

    @Builder
    public TypingResult(Member member, TypingText typingText, int accuracy, int actualAccuracy, int elapsedMs, int cpm, String matchPerChar, String elapsedMsPerChar) {
        this.member = member;
        this.typingText = typingText;
        this.accuracy = accuracy;
        this.actualAccuracy = actualAccuracy;
        this.elapsedMs = elapsedMs;
        this.cpm = cpm;
        this.matchPerChar = matchPerChar;
        this.elapsedMsPerChar = elapsedMsPerChar;
    }
}
