package com.qap.golf_club_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberName;
    private String address;
    private String emailAddress;
    private String phoneNumber;
    private String startDateOfMembership;
    private String durationOfMembership;

    @ManyToMany(mappedBy = "participatingMembers")
    @JsonIgnoreProperties("participatingMembers")
    private Set<Tournament> tournaments = new HashSet<>();
}
