package com.qap.golf_club_api.controller;

import com.qap.golf_club_api.model.Member;
import com.qap.golf_club_api.model.Tournament;
import com.qap.golf_club_api.repository.MemberRepository;
import com.qap.golf_club_api.repository.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentRepository tournamentRepository;
    private final MemberRepository memberRepository;

    public TournamentController(TournamentRepository tournamentRepository, MemberRepository memberRepository) {
        this.tournamentRepository = tournamentRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament) {
        Tournament saved = tournamentRepository.save(tournament);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @PostMapping("/{tournamentId}/members/{memberId}")
    public ResponseEntity<Void> assignMemberToTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId
    ) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        tournament.getParticipatingMembers().add(member);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Tournament> searchTournaments(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String location
    ) {
        if (startDate != null) {
            return tournamentRepository.findByStartDate(startDate);
        } else if (location != null) {
            return tournamentRepository.findByLocationContainingIgnoreCase(location);
        }
        // fallback: return all tournaments if no param provided
        return tournamentRepository.findAll();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<Set<Member>> getTournamentMembers(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(tournament -> ResponseEntity.ok(tournament.getParticipatingMembers()))
                .orElse(ResponseEntity.notFound().build());
    }
}
