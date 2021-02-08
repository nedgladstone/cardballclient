package com.github.nedgladstone.cardballclient.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Action {
    private Long id;

    //@JsonBackReference(value = "results-in-cause")
    private Action cause;

    //@JsonManagedReference(value = "results-in-cause")
    private List<Action> results;

    //@JsonBackReference(value = "action-in-game")
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
