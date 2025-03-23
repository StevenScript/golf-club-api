package com.qap.golf_club_api.model;

import com.qap.golf_club_api.model.Tournament;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TournamentTest {

    @Test
    void testTournamentFields() {
        Tournament t = new Tournament();
        t.setStartDate("2023-06-01");
        t.setEndDate("2023-06-05");
        t.setLocation("Pebble Beach");
        t.setEntryFee(200.0);
        t.setCashPrizeAmount(10000.0);

        assertEquals("2023-06-01", t.getStartDate());
        assertEquals("2023-06-05", t.getEndDate());
        assertEquals("Pebble Beach", t.getLocation());
        assertEquals(200.0, t.getEntryFee());
        assertEquals(10000.0, t.getCashPrizeAmount());
    }
}
