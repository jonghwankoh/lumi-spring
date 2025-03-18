package com.jonghwan.typing.domain.sentence;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MySentence extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "meaning_id", nullable = false)
    private SentenceMeaning meaning;

    @Builder
    public MySentence(Member member, SentenceMeaning meaning) {
        this.member = member;
        this.meaning = meaning;
    }
}
