package com.github.nedgladstone.cardballclient.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class SideStatus {
    private int runs = 0;

    private int hits = 0;

    private int errors = 0;

    // The following fields are set to the slot number in the batting order
    // Or 0 if unoccupied
    private int batter = 0;

    private int onFirst = 0;

    private int onSecond = 0;

    private int onThird = 0;
}
