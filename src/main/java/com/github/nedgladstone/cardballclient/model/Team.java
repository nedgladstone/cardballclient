package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class Team {
    private Long id;

    private TeamDefinition definition;

    private int winsThisSeason = 0;

    private int lossesThisSeason = 0;

    @JsonManagedReference
    private List<Player> players = new ArrayList<>();
}
