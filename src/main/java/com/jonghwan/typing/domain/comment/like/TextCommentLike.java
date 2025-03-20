package com.jonghwan.typing.domain.comment.like;

import com.jonghwan.typing.domain.comment.TextComment;
import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextCommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id", nullable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name="comment_id", nullable = false)
    private Long textCommentId;

    @ManyToOne
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private TextComment textComment;

    @Builder
    public TextCommentLike(Long memberId, Long textCommentId) {
        this.memberId = memberId;
        this.textCommentId = textCommentId;
    }
}
