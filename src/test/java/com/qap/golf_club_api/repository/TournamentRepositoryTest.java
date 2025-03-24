package com.qap.golf_club_api.repository;


import com.qap.golf_club_api.model.Tournament;
import com.qap.golf_club_api.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void cleanUp() {
        tournamentRepository.deleteAll();
    }

    @Test
    void testSaveAndFindTournament() {
        Tournament t = new Tournament();
        t.setStartDate("2023-07-01");
        t.setEndDate("2023-07-05");
        t.setLocation("Augusta");
        t.setEntryFee(300.0);
        t.setCashPrizeAmount(15000.0);

        // Save
        Tournament saved = tournamentRepository.save(t);
        assertNotNull(saved.getId());

        // Retrieve
        Tournament found = tournamentRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Augusta", found.getLocation());
        assertEquals(15000.0, found.getCashPrizeAmount());
    }
}
