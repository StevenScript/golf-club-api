package com.qap.golf_club_api.model;

import jakarta.persistence.*;
import lombok.*;

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
}
