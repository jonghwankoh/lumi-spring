package com.jonghwan.typing.domain.comment;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.domain.typingtext.TypingText;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TextComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @ManyToOne
    @JoinColumn(name = "text_id", nullable = false)
    private TypingText typingText;

    @Column(nullable = false)
    private String content;
}
