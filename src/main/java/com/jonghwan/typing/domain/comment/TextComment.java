package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.member.Member;
import com.jonghwan.typing.domain.typingtext.TypingText;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id", nullable = false)
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name="text_id", nullable = false)
    private Long textId;
    @ManyToOne
    @JoinColumn(name = "text_id", insertable = false, updatable = false)
    private TypingText typingText;

    @Column(nullable = false)
    private String content;

    @Builder
    public TextComment(Long memberId, Long textId, String content) {
        this.memberId = memberId;
        this.textId = textId;
        this.content = content;
    }
}
