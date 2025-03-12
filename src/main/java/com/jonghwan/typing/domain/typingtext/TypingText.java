package com.jonghwan.typing.domain.typingtext;

import com.jonghwan.typing.shared.security.Member;
import com.jonghwan.typing.shared.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Setter
@Entity
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
}
