package com.SE3_NachhilfeApp.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    @Query("Select s from Member s Where s.name = ?1")
    Optional<Member> findMemberByName(String name);

}
