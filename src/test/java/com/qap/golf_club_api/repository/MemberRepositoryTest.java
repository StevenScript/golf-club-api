package com.qap.golf_club_api.repository;

import com.qap.golf_club_api.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setup() {
        // Clean up the repositories before each test
        tournamentRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void testSaveAndFindMember() {
        // Create a new Member
        Member m = new Member();
        m.setMemberName("Alice");
        m.setAddress("456 Fairway");
        m.setEmailAddress("alice@example.com");
        m.setPhoneNumber("555-5678");
        m.setStartDateOfMembership("2023-02-01");
        m.setDurationOfMembership("1 year");

        // Save
        Member saved = memberRepository.save(m);
        assertNotNull(saved.getId());

        // Retrieve
        Member found = memberRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Alice", found.getMemberName());
    }
}
