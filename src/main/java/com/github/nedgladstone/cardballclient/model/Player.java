package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
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

    //@JsonBackReference(value = "player-in-team")
    private Team team;
}
