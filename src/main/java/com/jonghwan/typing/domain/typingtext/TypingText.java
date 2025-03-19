package com.jonghwan.typing.domain.typingtext;

import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.shared.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Check(constraints = "content REGEXP '^[ A-Za-z0-9{}\\\\[\\\\]?.,;:|()*~`!^\\\\-_+<>@#$%&=\\'\\\"]*$'")
public class TypingText extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    public Long getAuthorId() {
        return author != null ? author.getId() : null;
    }

    @Builder
    public TypingText(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
