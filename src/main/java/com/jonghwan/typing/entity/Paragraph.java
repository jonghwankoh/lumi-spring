package com.jonghwan.typing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@Immutable
public class Paragraph {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;

}
