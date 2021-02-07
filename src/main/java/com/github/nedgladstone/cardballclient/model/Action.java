package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class Action {
    private Long id;

    @JsonBackReference
    private Action cause;

    @JsonManagedReference
    private List<Action> results;

    @JsonBackReference
    private Game game;

    private int inning;

    private int half;

    private int batter;

    private Player player;

    private Timestamp time;

    private int fromBase;

    private int toBase;

    // Batter outcomes
    //KL: Strikeout looking
    //KS: Strikeout swinging
    //GO: Ground out
    //LO: Line out
    //FO: Fly out
    //PO: Pop out
    //BB: Base on balls
    //HB: Hit batsman
    //CI: Reached on catcher's interference
    //RE: Reached on error
    //RK: Reached on dropped third strike
    //FC: Hit into fielder's choice
    //SC: Sacrifice fly or bunt
    //HI: Hit
    //GR: Ground rule hit

    // Runner outcomes
    //PK: Picked off
    //CS: Caught stealing
    //SB: Stolen base
    //AO: Advanced on out
    //AE: Advanced on error
    //AB: Advanced on balk
    //AW: Advanced on wild pitch
    //AP: Advanced on passed ball
    //AH: Advanced on hit
    private String outcome;

    private String fielderTouches;

    private int errorOnFielder;

    private boolean isRun;

    private boolean isOut;
}
