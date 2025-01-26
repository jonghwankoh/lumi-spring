package com.jonghwan.typing.domain.sentence;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SentenceMeaning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sentence;

    @Column(nullable = false)
    private String translation;
}