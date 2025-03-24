package com.qap.golf_club_api.repository;

import com.qap.golf_club_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMemberNameContainingIgnoreCase(String name);
    List<Member> findByPhoneNumberContaining(String phone);

    @Query("SELECT DISTINCT m FROM Member m JOIN m.tournaments t WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") String startDate);
}
