package com.jonghwan.typing.domain.vocab;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import com.jonghwan.typing.shared.security.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VocabMeaning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="vocab_id", nullable = false)
    private Vocab vocab;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="editor_id", nullable = true)
    private Member editor;
}