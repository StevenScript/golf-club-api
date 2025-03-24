package com.qap.golf_club_api.repository;

import com.qap.golf_club_api.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByStartDate(String startDate);
    List<Tournament> findByLocationContainingIgnoreCase(String location);

}
