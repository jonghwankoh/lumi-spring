package com.jonghwan.typing.shared.security.member;

import com.jonghwan.typing.shared.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_key", nullable = false)
    private String userKey;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Builder
    public Member(String userKey, String name, String email, String role) {
        this.userKey = userKey;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void mergeUserInfo(String name, String email) {
        if (!this.name.equals(name)) {
            this.name = name;
        }
        if (!this.email.equals(email)) {
            this.email = email;
        }
    }
}
