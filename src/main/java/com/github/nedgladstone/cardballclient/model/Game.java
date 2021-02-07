package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
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

    private Team visitingTeam = null;

    private Team homeTeam = null;

    @JsonManagedReference
    private List<Participant> visitingLineup = new ArrayList<>();

    @JsonManagedReference
    private List<Participant> homeLineup = new ArrayList<>();

    private GameStatus status = new GameStatus();

    private String offensiveStrategy = null;
    private String defensiveStrategy = null;

    @JsonManagedReference
    private List<Action> actions = new ArrayList<>();
}
