package com.github.nedgladstone.cardballclient.model;

import com.github.nedgladstone.cardballclient.controller.GameRunner;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ParticipantDefinition {
    // 0-8, with 0 batting first
    private int battingOrderSlot;

    // 0-8, with position + 1 being scorecard numbering
    private int fieldingPosition;

    private long playerId;

    public int compareBattingSlotTo(ParticipantDefinition other) {
        return battingOrderSlot - other.battingOrderSlot;
    }

    public int compareFieldingPositionTo(ParticipantDefinition other) {
        return fieldingPosition - other.fieldingPosition;
    }
}
