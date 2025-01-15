package com.jonghwan.typing.entity.vocab;

import com.jonghwan.typing.entity.BaseEntity;
import com.jonghwan.typing.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MyVocab extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "meaning_id", nullable = false)
    private VocabMeaning meaning;
}
