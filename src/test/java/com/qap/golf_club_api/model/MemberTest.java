package com.qap.golf_club_api.model;

import com.qap.golf_club_api.model.Member;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    void testMemberFields() {
        Member member = new Member();
        member.setMemberName("John Doe");
        member.setAddress("123 Golf St.");
        member.setEmailAddress("john@example.com");
        member.setPhoneNumber("555-1234");
        member.setStartDateOfMembership("2023-01-01");
        member.setDurationOfMembership("1 year");

        assertEquals("John Doe", member.getMemberName());
        assertEquals("123 Golf St.", member.getAddress());
        assertEquals("john@example.com", member.getEmailAddress());
        assertEquals("555-1234", member.getPhoneNumber());
        assertEquals("2023-01-01", member.getStartDateOfMembership());
        assertEquals("1 year", member.getDurationOfMembership());
    }
}
