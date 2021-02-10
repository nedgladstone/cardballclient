package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Game {
    public enum Role {
        OFFENSE,
        DEFENSE;
    }

    public enum Side {
        VISITOR,
        HOME;
    }

    private Long id;

    private String name;

    // experiment
    // @JsonManagedReference(value = "team-in-game")
    //@JsonIgnoreProperties("game")
    private Team visitingTeam = null;

    // experiment
    // @JsonManagedReference(value = "team-in-game")
    //@JsonIgnoreProperties("game")
    private Team homeTeam = null;

    @JsonManagedReference(value = "participant-in-game")
    @JsonIgnoreProperties("game")
    private List<Participant> visitingLineup = new ArrayList<>();

    @JsonManagedReference(value = "participant-in-game")
    @JsonIgnoreProperties("game")
    private List<Participant> homeLineup = new ArrayList<>();

    private GameStatus status = new GameStatus();

    private String offensiveStrategy = null;
    private String defensiveStrategy = null;

    //@JsonManagedReference(value = "action-in-game")
    private List<Action> actions = new ArrayList<>();

    public String myString() {
        return "Game: " + id + " " + name + " vtl " + visitingLineup.size() + " htl " + homeLineup.size();
    }
}
