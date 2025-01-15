package com.jonghwan.typing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
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
    private String matchPerLetter;

    @Column(columnDefinition = "json", nullable = false)
    private String elapsedMsPerLetter;
}
