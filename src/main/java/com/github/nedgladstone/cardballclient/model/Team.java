package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Team {
    private Long id;

    private TeamDefinition definition;

    private int winsThisSeason = 0;

    private int lossesThisSeason = 0;

    //@JsonManagedReference(value = "player-in-team")
    private List<Player> players = new ArrayList<>();

    // experiment
    //@JsonBackReference(value = "team-in-game")
    //@JsonManagedReference(value = "team-in-game")
    //@JsonIgnoreProperties({"visitingTeam", "homeTeam"})
    private Game game;
}
