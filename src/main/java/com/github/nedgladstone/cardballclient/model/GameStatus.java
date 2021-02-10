package com.github.nedgladstone.cardballclient.model;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class GameStatus {
    public enum State {
        ACCEPTING_LINEUPS,
        ACCEPTING_STRATEGIES,
        BUSY,
        PLAYING,
        GAME_OVER
    }

    private State state = State.ACCEPTING_LINEUPS;

    private int inning = 1;

    private int half = 0;

    private int runs = 0;

    private int hits = 0;

    private int errors = 0;

    private int outs = 0;

    private int balls = 0;

    private int strikes = 0;

    // The following fields are set to the slot number in the batting order
    // Or 0 if unoccupied
    private int batter = 0;

    private int onFirst = 0;

    private int onSecond = 0;

    private int onThird = 0;
}
