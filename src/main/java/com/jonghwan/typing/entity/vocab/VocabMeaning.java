package com.jonghwan.typing.entity.vocab;

import com.jonghwan.typing.entity.BaseEntity;
import com.jonghwan.typing.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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