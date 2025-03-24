package com.qap.golf_club_api.controller;


import com.qap.golf_club_api.model.Member;
import com.qap.golf_club_api.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member saved = memberRepository.save(member);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
