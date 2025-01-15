package com.jonghwan.typing.entity;

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
    @JoinColumn(name = "author_id", nullable = true)
    private Member author;
}
