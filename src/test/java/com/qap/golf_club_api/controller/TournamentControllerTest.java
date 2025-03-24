package com.qap.golf_club_api.controller;


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
}
