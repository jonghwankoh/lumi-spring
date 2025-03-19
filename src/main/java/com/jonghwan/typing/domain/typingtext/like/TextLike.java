package com.jonghwan.typing.domain.typingtext.like;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.member.Member;
import com.jonghwan.typing.domain.typingtext.TypingText;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "text_like",
        uniqueConstraints = @UniqueConstraint(name = "unique_member_text", columnNames = {"member_id", "text_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "text_id", nullable = false)
    private TypingText typingText;

    @Builder
    public TextLike(Member member, TypingText typingText) {
        this.member = member;
        this.typingText = typingText;
    }
}
