package com.github.nedgladstone.cardballclient.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class LineupDefinition {
    private List<ParticipantDefinition> participants = new ArrayList<>();
}
