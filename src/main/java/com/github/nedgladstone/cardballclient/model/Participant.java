package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class Participant {
    private Long id;

    @JsonBackReference
    private Game game;

    // -1 - -9 = batting order slot before being substituted out
    // 0 = not in the batting order (has not been in game, or is pitcher with DH)
    // 1 - 9 = batting order slot with 1 batting first
   private int battingOrderSlot;

    // -1 - -9 = fielding position before being substituted out
    // 0 = not fielding (has not been in game, or is the DH)
    // 1 - 9 = fielding position per scorecard notation
    private int fieldingPosition;

    @JsonIdentityReference(alwaysAsId = true)
    private Player player;
}