package com.qap.golf_club_api.controller;

import com.qap.golf_club_api.model.Member;
import com.qap.golf_club_api.model.Tournament;
import com.qap.golf_club_api.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TournamentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void cleanUp() {
        tournamentRepository.deleteAll();
    }

    @Test
    void testAddAndGetTournament() {
        Tournament t = new Tournament();
        t.setStartDate("2023-08-01");
        t.setEndDate("2023-08-10");
        t.setLocation("TPC Sawgrass");
        t.setEntryFee(400.0);
        t.setCashPrizeAmount(20000.0);

        // POST /api/tournaments
        ResponseEntity<Tournament> postResponse =
                restTemplate.postForEntity("/api/tournaments", t, Tournament.class);
        assertEquals(200, postResponse.getStatusCodeValue());
        Tournament saved = postResponse.getBody();
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("TPC Sawgrass", saved.getLocation());

        // GET /api/tournaments/{id}
        ResponseEntity<Tournament> getResponse =
                restTemplate.getForEntity("/api/tournaments/" + saved.getId(), Tournament.class);
        assertEquals(200, getResponse.getStatusCodeValue());
        Tournament found = getResponse.getBody();
        assertNotNull(found);
        assertEquals("TPC Sawgrass", found.getLocation());
    }

    @Test
    void testAssignMemberToTournament() {
        // 1) Create a Tournament
        Tournament t = new Tournament();
        t.setStartDate("2023-09-01");
        t.setEndDate("2023-09-05");
        t.setLocation("Some Course");
        t.setEntryFee(500.0);
        t.setCashPrizeAmount(30000.0);

        ResponseEntity<Tournament> tournamentResponse =
                restTemplate.postForEntity("/api/tournaments", t, Tournament.class);
        Tournament savedTournament = tournamentResponse.getBody();
        assertNotNull(savedTournament);
        assertNotNull(savedTournament.getId());

        // 2) Create a Member
        Member m = new Member();
        m.setMemberName("Charlie");
        m.setAddress("123 Lane");
        m.setEmailAddress("charlie@example.com");
        m.setPhoneNumber("555-1111");
        m.setStartDateOfMembership("2023-04-01");
        m.setDurationOfMembership("1 year");

        ResponseEntity<Member> memberResponse =
                restTemplate.postForEntity("/api/members", m, Member.class);
        Member savedMember = memberResponse.getBody();
        assertNotNull(savedMember);
        assertNotNull(savedMember.getId());

        // 3) Assign member to the tournament
        restTemplate.postForEntity(
                "/api/tournaments/" + savedTournament.getId() + "/members/" + savedMember.getId(),
                null,
                Void.class
        );

        // 4) Verify the member is now in the tournament
        ResponseEntity<Tournament> getResponse =
                restTemplate.getForEntity("/api/tournaments/" + savedTournament.getId(), Tournament.class);
        Tournament updatedTournament = getResponse.getBody();
        assertNotNull(updatedTournament);
        assertEquals(1, updatedTournament.getParticipatingMembers().size());
    }
}
