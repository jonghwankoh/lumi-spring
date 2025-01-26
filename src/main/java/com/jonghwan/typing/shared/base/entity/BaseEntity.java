package com.jonghwan.typing.shared.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}