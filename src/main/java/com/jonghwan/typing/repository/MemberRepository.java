package com.jonghwan.typing.repository;

import com.jonghwan.typing.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByUsername(String username);
}
