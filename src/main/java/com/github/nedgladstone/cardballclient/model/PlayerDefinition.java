package com.github.nedgladstone.cardballclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class PlayerDefinition {
    private String lastName;
    private String firstName;
    private long mlbPlayerId;
    private int year;
}
