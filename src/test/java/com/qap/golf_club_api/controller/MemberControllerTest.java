package com.qap.golf_club_api.controller;



import com.qap.golf_club_api.model.Member;
import com.qap.golf_club_api.model.Tournament;
import com.qap.golf_club_api.repository.MemberRepository;
import com.qap.golf_club_api.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setup() {
        tournamentRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void testAddAndGetMember() {
        Member m = new Member();
        m.setMemberName("Bob");
        m.setAddress("789 Greenway");
        m.setEmailAddress("bob@example.com");
        m.setPhoneNumber("555-9999");
        m.setStartDateOfMembership("2023-03-01");
        m.setDurationOfMembership("1 year");

        // POST /api/members
        ResponseEntity<Member> postResponse =
                restTemplate.postForEntity("/api/members", m, Member.class);
        assertEquals(200, postResponse.getStatusCodeValue());
        Member saved = postResponse.getBody();
        assertNotNull(saved);
        assertNotNull(saved.getId());

        // GET /api/members/{id}
        ResponseEntity<Member> getResponse =
                restTemplate.getForEntity("/api/members/" + saved.getId(), Member.class);
        assertEquals(200, getResponse.getStatusCodeValue());
        Member found = getResponse.getBody();
        assertNotNull(found);
        assertEquals("Bob", found.getMemberName());
    }

    @Test
    void testSearchMembersByName() {
        // 1) Create two Members
        Member m1 = new Member();
        m1.setMemberName("Alice");
        m1.setAddress("111 Fairway");
        m1.setEmailAddress("alice@example.com");
        m1.setPhoneNumber("555-1111");
        m1.setStartDateOfMembership("2023-01-01");
        m1.setDurationOfMembership("1 year");

        Member m2 = new Member();
        m2.setMemberName("Bob");
        m2.setAddress("222 Fairway");
        m2.setEmailAddress("bob@example.com");
        m2.setPhoneNumber("555-2222");
        m2.setStartDateOfMembership("2023-02-01");
        m2.setDurationOfMembership("2 years");

        restTemplate.postForEntity("/api/members", m1, Member.class);
        restTemplate.postForEntity("/api/members", m2, Member.class);

        // 2) Search by name containing "Ali"
        // We might define an endpoint: /api/members/search?name=Ali
        ResponseEntity<Member[]> response = restTemplate.getForEntity("/api/members/search?name=Ali", Member[].class);
        assertEquals(200, response.getStatusCodeValue());
        Member[] results = response.getBody();
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals("Alice", results[0].getMemberName());
    }

    @Test
    void testSearchMembersByPhone() {
        // 1) Create a Member
        Member m = new Member();
        m.setMemberName("Charlie");
        m.setAddress("444 Golf St");
        m.setEmailAddress("charlie@example.com");
        m.setPhoneNumber("555-4444");
        m.setStartDateOfMembership("2023-03-01");
        m.setDurationOfMembership("1 year");

        restTemplate.postForEntity("/api/members", m, Member.class);

        // 2) Search by phone containing "4444"
        ResponseEntity<Member[]> response = restTemplate.getForEntity("/api/members/search?phone=4444", Member[].class);
        assertEquals(200, response.getStatusCodeValue());
        Member[] found = response.getBody();
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals("Charlie", found[0].getMemberName());
    }

    @Test
    void testSearchMembersByTournamentStartDate() {
        // 1) Create a new Tournament that starts 2023-10-01
        Tournament t = new Tournament();
        t.setStartDate("2023-10-01");
        t.setEndDate("2023-10-05");
        t.setLocation("TestCourse");
        t.setEntryFee(100.0);
        t.setCashPrizeAmount(5000.0);
        ResponseEntity<Tournament> postTournament = restTemplate.postForEntity("/api/tournaments", t, Tournament.class);
        Tournament savedTournament = postTournament.getBody();
        assertNotNull(savedTournament);
        assertNotNull(savedTournament.getId());

        // 2) Create a Member
        Member m = new Member();
        m.setMemberName("Diana");
        m.setAddress("777 Golf Rd");
        m.setEmailAddress("diana@example.com");
        m.setPhoneNumber("555-7777");
        m.setStartDateOfMembership("2023-01-01");
        m.setDurationOfMembership("1 year");
        ResponseEntity<Member> postMember = restTemplate.postForEntity("/api/members", m, Member.class);
        Member savedMember = postMember.getBody();
        assertNotNull(savedMember);
        assertNotNull(savedMember.getId());

        // 3) Assign the Member to the Tournament
        restTemplate.postForEntity(
                "/api/tournaments/" + savedTournament.getId() + "/members/" + savedMember.getId(),
                null,
                Void.class
        );

        // 4) Search by tournament start date (2023-10-01)
        // We'll assume the endpoint is /api/members/search?tournamentStart=2023-10-01
        ResponseEntity<Member[]> response = restTemplate.getForEntity("/api/members/search?tournamentStart=2023-10-01", Member[].class);
        assertEquals(200, response.getStatusCodeValue());

        Member[] found = response.getBody();
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals("Diana", found[0].getMemberName());
    }
}
