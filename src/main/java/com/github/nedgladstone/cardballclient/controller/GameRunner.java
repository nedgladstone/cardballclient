package com.github.nedgladstone.cardballclient.controller;

import com.github.nedgladstone.cardballclient.client.GameClient;
import com.github.nedgladstone.cardballclient.client.PlayerClient;
import com.github.nedgladstone.cardballclient.client.TeamClient;
import com.github.nedgladstone.cardballclient.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@ExecuteOn(TaskExecutors.IO)
@Controller("/cardball")
public class GameRunner {
    protected final PlayerClient playerClient;
    protected final TeamClient teamClient;
    protected final GameClient gameClient;

    public GameRunner(PlayerClient playerClient, TeamClient teamClient, GameClient gameClient) {
        this.playerClient = playerClient;
        this.teamClient = teamClient;
        this.gameClient = gameClient;
    }

    @Get("/start")
    public String start() {
        StringBuilder out = new StringBuilder();
        List<PlayerDefinition> playerDefs = Arrays.asList(
                new PlayerDefinition("Amy", "One", -1, 1901),
                new PlayerDefinition("Baker", "Two", -1, 1902),
                new PlayerDefinition("Charles", "Three", -1, 1903),
                new PlayerDefinition("Darlene", "Four", -1, 1904),
                new PlayerDefinition("Ethel", "Five", -1, 1905),
                new PlayerDefinition("Frank", "Six", -1, 1906),
                new PlayerDefinition("Ginny", "Seven", -1, 1907),
                new PlayerDefinition("Harry", "Eight", -1, 1908),
                new PlayerDefinition("Ignatius", "Nine", -1, 1909),
                new PlayerDefinition("Joe", "Ten", -1, 1910),
                new PlayerDefinition("Kelly", "Eleven", -1, 1911),
                new PlayerDefinition("Lou", "Twelve", -1, 1912),
                new PlayerDefinition("Mike", "Thirteen", -1, 1913),
                new PlayerDefinition("Ned", "Fourteen", -1, 1914),
                new PlayerDefinition("Opal", "Fifteen", -1, 1915),
                new PlayerDefinition("Patty", "Sixteen", -1, 1916),
                new PlayerDefinition("Quinn", "Seventeen", -1, 1917),
                new PlayerDefinition("Rowdy", "Eighteen", -1, 1918));
        out.append("Creating Players\n");
        List<Player> createdPlayers = playerDefs.stream().map(pd -> playerClient.create(pd)).collect(Collectors.toList());
        createdPlayers.stream().forEach(p -> out.append(p.toString()));

        out.append("\nListing Players\n");
        Iterable<Player> fetchedPlayers = playerClient.list();
        StreamSupport.stream(fetchedPlayers.spliterator(), false).forEach(p -> out.append(p.toString() + "\n"));
        List<TeamDefinition> teamDefs = Arrays.asList(
                new TeamDefinition("Colorado", "Rockies", "Ned", "Gladstone"),
                new TeamDefinition("Philadelphia", "Phillies", "Ed", "Gladstone"));

        out.append("\nCreating Teams");
        List<Team> createdTeams = teamDefs.stream().map(td -> teamClient.create(td)).collect(Collectors.toList());
        createdTeams.stream().forEach(t -> out.append(t.toString()));

        out.append("\nListing Teams\n");
        Iterable<Team> fetchedTeams = teamClient.list();
        StreamSupport.stream(fetchedTeams.spliterator(), false).forEach(t -> out.append(t.toString() + "\n"));

        GameDefinition gameDef = new GameDefinition("Kewl game created by cardballclient", createdTeams.get(0).getId(), createdTeams.get(1).getId());
        out.append("\nCreating a Game\n");
        Game createdGame = gameClient.create(gameDef);
        out.append(createdGame.toString());

        out.append("\nListing Games\n");
        Iterable<Game> fetchedGames = gameClient.list();
        StreamSupport.stream(fetchedGames.spliterator(), false).forEach(g -> out.append(g.toString() + "\n"));

        List<LineupDefinition> lineupDefs = IntStream.iterate(0, i -> i + 1)
                .limit(2)
                .mapToObj(i -> new LineupDefinition(IntStream.iterate(i * 9, j -> j + 1)
                        .limit(9)
                        .mapToObj(j -> new ParticipantDefinition(j % 9, 9 - j % 9, createdPlayers.get(j).getId()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        out.append("\nMake sure Lineup Definitions got built properly before using them\n");
        lineupDefs.stream().forEach(ld -> out.append(ld.toString()));

        out.append("\nAdding visitor lineup\n");
        GameStatus statusAfterAddingVisitorLineup = gameClient.putLineup(createdGame.getId(), "visitor", lineupDefs.get(0));
        out.append(statusAfterAddingVisitorLineup);

        out.append("\nAdding home lineup\n");
        GameStatus statusAfterAddingHomeLineup = gameClient.putLineup(createdGame.getId(), "home", lineupDefs.get(0));
        out.append(statusAfterAddingHomeLineup);

        out.append("\nRetrieving game by id\n");
        Game foundGame = gameClient.find(createdGame.getId());
        out.append(foundGame.toString());

        out.append("\nReady to play!\n");
        return out.toString();
    }

    @Get("/play")
    public String play() {
        return "Not implemented yet";
    }
}
