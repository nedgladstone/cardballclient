package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class Player {
    private Long id;

    private String firstName;

    private String lastName;

    private int year;

    private int position;

    // R: right, L: left, r: right/switch, l: left/switch, A: ambidextrous
    private char battingHand;

    // R: right, L: left, r: right/switch, l: left/switch, A: ambidextrous
    private char throwingHand;

    private int battingAverage;

    private int era;

    @JsonBackReference
    private Team team;
}
