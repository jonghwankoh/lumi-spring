package com.jonghwan.typing.domain.comment.like;

import com.jonghwan.typing.domain.comment.TextComment;
import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextCommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private TextComment textComment;

    @Builder
    public TextCommentLike(Member member, TextComment textComment) {
        this.member = member;
        this.textComment = textComment;
    }
}
