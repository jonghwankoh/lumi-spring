package com.jonghwan.typing.domain.typingresult;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.member.Member;
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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "text_id", nullable = false)
    private TypingText textId;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "match_per_char", columnDefinition = "json", nullable = false)
    private String matchPerCharJson;

    @Column(name = "elapsed_ms_per_char", columnDefinition = "json", nullable = false)
    private String elapsedMsPerCharJson;

    @Builder
    public TypingResult(Long memberId, Long textId, int accuracy, int actualAccuracy, int elapsedMs, int cpm, String matchPerCharJson, String elapsedMsPerCharJson) {
        this.memberId = memberId;
        this.textId = textId;
        this.accuracy = accuracy;
        this.actualAccuracy = actualAccuracy;
        this.elapsedMs = elapsedMs;
        this.cpm = cpm;
        this.matchPerCharJson = matchPerCharJson;
        this.elapsedMsPerCharJson = elapsedMsPerCharJson;
    }
}
