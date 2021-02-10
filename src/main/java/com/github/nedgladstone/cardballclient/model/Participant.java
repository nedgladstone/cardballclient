package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Participant {
    private Long id;

    @JsonBackReference(value = "participant-in-game")
    //@JsonIgnore
    private Game game;

    // -1 - -9 = batting order slot before being substituted out
    // 0 = not in the batting order (has not been in game, or is pitcher with DH)
    // 1 - 9 = batting order slot with 1 batting first
   private int battingOrderSlot;

    // -1 - -9 = fielding position before being substituted out
    // 0 = not fielding (has not been in game, or is the DH)
    // 1 - 9 = fielding position per scorecard notation
    private int fieldingPosition;

    //RESTORE?@JsonIdentityReference(alwaysAsId = true)
    private Player player;

    public int compareBattingSlotTo(Participant other) {
        return battingOrderSlot - other.battingOrderSlot;
    }

    public int compareFieldingPositionTo(Participant other) {
        return fieldingPosition - other.fieldingPosition;
    }
/*
    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", game=" + game +
                ", battingOrderSlot=" + battingOrderSlot +
                ", fieldingPosition=" + fieldingPosition +
                ", player=" + player +
                '}';
    }

 */
}
