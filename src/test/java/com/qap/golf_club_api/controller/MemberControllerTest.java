package com.qap.golf_club_api.controller;



import com.qap.golf_club_api.model.Member;
import com.qap.golf_club_api.repository.MemberRepository;
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

    @BeforeEach
    void setup() {
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
}
