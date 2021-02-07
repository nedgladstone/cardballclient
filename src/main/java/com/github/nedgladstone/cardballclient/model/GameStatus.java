package com.github.nedgladstone.cardballclient.model;

import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
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

    private SideStatus visitorStatus = new SideStatus();

    private SideStatus homeStatus = new SideStatus();
}
