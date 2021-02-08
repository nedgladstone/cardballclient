package com.github.nedgladstone.cardballclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nedgladstone.cardballclient.client.GameClient;
import com.github.nedgladstone.cardballclient.client.PlayerClient;
import com.github.nedgladstone.cardballclient.client.TeamClient;
import com.github.nedgladstone.cardballclient.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@ExecuteOn(TaskExecutors.IO)
@Controller("/cardball")
public class GameRunner {
    private static final Logger logger = LoggerFactory.getLogger("com.github.nedgladstone.cardballclient");

    protected final PlayerClient playerClient;
    protected final TeamClient teamClient;
    protected final GameClient gameClient;

    private List<Player> alPlayers;
    private List<Player> nlPlayers;
    private LineupDefinition alLineup;
    private LineupDefinition nlLineup;
    private Team alTeam;
    private Team nlTeam;

    public GameRunner(PlayerClient playerClient, TeamClient teamClient, GameClient gameClient) {
        this.playerClient = playerClient;
        this.teamClient = teamClient;
        this.gameClient = gameClient;
    }

    @RequiredArgsConstructor
    class Ballplayer {
        public final String firstName;
        public final String lastName;
        public final int year;
        public final int battingOrderSlot;
        public final int fieldingPosition;
        public Player player = null;
        public PlayerDefinition toPlayerDef() {
            return new PlayerDefinition(lastName, firstName, -1, year);
        }
        public ParticipantDefinition toParticipantDef() {
            return new ParticipantDefinition(battingOrderSlot, fieldingPosition, player.getId());
        }
    }

    @Get("/teams")
    String createPlayers() {
        StringBuilder out = new StringBuilder();
        String alName = "AL All-Time All-Stars";
        String nlName = "NL All-Time All-Stars";
        try {
            logger.info("Creating players");
            List<Ballplayer> alBallplayers = Arrays.asList(
                    new Ballplayer("Ivan", "Rodriguez", 1999, 8, 2),
                    new Ballplayer("Lou", "Gehrig", 1936, 3, 3),
                    new Ballplayer("Eddie", "Collins", 1914, 1, 4),
                    new Ballplayer("Cal", "Ripken Jr.", 1991, 2, 6),
                    new Ballplayer("Alex", "Rodriguez", 2005, 5, 5),
                    new Ballplayer("Babe", "Ruth", 1923, 4, 9),
                    new Ballplayer("Ted", "Williams", 1946, 6, 7),
                    new Ballplayer("Ty", "Cobb", 1911, 7, 8),
                    new Ballplayer("Roger", "Clemens", 1986, 9, 1));
            List<Ballplayer> nlBallplayers = Arrays.asList(
                    new Ballplayer("Johnny", "Bench", 1972, 8, 2),
                    new Ballplayer("Albert", "Pujols", 2008, 7, 3),
                    new Ballplayer("Rogers", "Hornsby", 1925, 6, 4),
                    new Ballplayer("Barry", "Larkin", 1995, 5, 6),
                    new Ballplayer("Mike", "Schmidt", 1981, 3, 5),
                    new Ballplayer("Hank", "Aaron", 1974, 4, 7),
                    new Ballplayer("Stan", "Musial", 1946, 1, 9),
                    new Ballplayer("Willie", "Mays", 1965, 2, 8),
                    new Ballplayer("Gaylord", "Perry", 1977, 9, 1));
            alPlayers = createPlayers(alName, alBallplayers, out);
            nlPlayers = createPlayers(nlName, nlBallplayers, out);
            out.append("\n");

            // Create lineups
            alLineup = createLineup(alName, alBallplayers, out);
            nlLineup = createLineup(nlName, nlBallplayers, out);
            out.append("\n");

            // Create teams
            alTeam = createTeam(alName, "Ned", alLineup, out);
            nlTeam = createTeam(nlName, "Ed", nlLineup, out);

            return out.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    private List<Player> createPlayers(String teamName, List<Ballplayer> ballplayers, StringBuilder out) {
        List<Player> players = ballplayers.stream()
                .map(pd -> pd.player = playerClient.create(pd.toPlayerDef()))
                .collect(Collectors.toList());
        out.append("Created ").append(teamName).append(" players:\n").append(
                players.stream()
                        .map(p -> " " +
                                p.getId().toString() + " " +
                                p.getFirstName() + " " +
                                p.getLastName() + " " +
                                p.getYear())
                        .collect(Collectors.joining("\n"))).append("\n");
        return players;
    }

    private LineupDefinition createLineup(String teamName, List<Ballplayer> ballplayers, StringBuilder out) {
        LineupDefinition lineup = new LineupDefinition(ballplayers.stream()
                .map(Ballplayer::toParticipantDef)
                .collect(Collectors.toList()));
        out.append("Created ").append(teamName).append(" lineup:\n").append(
                ballplayers.stream()
                        .sorted()
                        .map(ps -> " " +
                                ps.battingOrderSlot + " " +
                                ps.fieldingPosition + " " +
                                ps.year + " " +
                                ps.firstName + " " +
                                ps.lastName + " " +
                                ps.player.getId())
                        .collect(Collectors.joining("\n"))).append("\n");

        return lineup;
    }

    private Team createTeam(String teamName, String manager, LineupDefinition lineup, StringBuilder out) {
        TeamDefinition teamDef = new TeamDefinition("", teamName, manager, "Gladstone");

        Team team = teamClient.create(teamDef);
        out.append("Created Team: ")
                .append(team.getDefinition().getName())
                .append("\n");
        return team;
    }

    @Get("/start")
    public String start() {
        StringBuilder out = new StringBuilder();
        try {
        logger.info("Starting start");
        logger.info("Creating players");
        out.append("Creating Players\n");
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
        List<Player> createdPlayers = playerDefs.stream().map(pd -> playerClient.create(pd)).collect(Collectors.toList());
        createdPlayers.stream().forEach(p -> out.append(p.toString()));

        logger.info("Listing players");
        out.append("\nListing Players\n");
        Iterable<Player> fetchedPlayers = playerClient.list();
        StreamSupport.stream(fetchedPlayers.spliterator(), false).forEach(p -> out.append(p.toString() + "\n"));
        List<TeamDefinition> teamDefs = Arrays.asList(
                new TeamDefinition("Colorado", "Rockies", "Ned", "Gladstone"),
                new TeamDefinition("Philadelphia", "Phillies", "Ed", "Gladstone"));

        logger.info("Creating teams");
        out.append("\nCreating Teams");
        List<Team> createdTeams = teamDefs.stream().map(td -> teamClient.create(td)).collect(Collectors.toList());
        createdTeams.stream().forEach(t -> out.append(t.toString()));

        logger.info("Listing teams");
        out.append("\nListing Teams\n");
        Iterable<Team> fetchedTeams = teamClient.list();
        StreamSupport.stream(fetchedTeams.spliterator(), false).forEach(t -> out.append(t.toString() + "\n"));

        logger.info("Creating a game");
        out.append("\nCreating a Game\n");
        GameDefinition gameDef = new GameDefinition("Kewl game created by cardballclient", createdTeams.get(0).getId(), createdTeams.get(1).getId());
        logger.info("- Sending the game creation and receiving game object back");
        Game createdGame = gameClient.create(gameDef);
        logger.info("- Received the game object");
        logger.info("-- Game received as " + createdGame.toString());
        out.append(createdGame.toString());

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(createdGame);
        Game g2 = mapper.readValue(result, Game.class);
        out.append("LOCAL Ser:\n" + result + "\nDeser:\n" + g2);

            logger.info("Retrieving game by id");
            out.append("\nRetrieving game by id\n");
            Game foundGame = gameClient.find(createdGame.getId());
            out.append(foundGame.toString());



        /*
        logger.info("Listing games AS STRING TO DEBUG!!!");
        out.append("\nListing Games AS STRING TO DEBUG!!!\n");
        String fetchedGames = gameClient.list();
        out.append(fetchedGames);
        //StreamSupport.stream(fetchedGames.spliterator(), false).forEach(g -> out.append(g.toString() + "\n"));
        */

        logger.info("Outputting lineup definitions");
        out.append("\nMake sure Lineup Definitions got built properly before using them\n");
        List<LineupDefinition> lineupDefs = IntStream.iterate(0, i -> i + 1)
                .limit(2)
                .mapToObj(i -> new LineupDefinition(IntStream.iterate(i * 9, j -> j + 1)
                        .limit(9)
                        .mapToObj(j -> new ParticipantDefinition(j % 9, 9 - j % 9, createdPlayers.get(j).getId()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        lineupDefs.stream().forEach(ld -> out.append(ld.toString()));

        logger.info("Adding visitor lineup to game");
        out.append("\nAdding visitor lineup\n");
        GameStatus statusAfterAddingVisitorLineup = gameClient.putLineup(createdGame.getId(), "visitor", lineupDefs.get(0));
        out.append(statusAfterAddingVisitorLineup);

        logger.info("Adding home lineup to game");
        out.append("\nAdding home lineup\n");
        GameStatus statusAfterAddingHomeLineup = gameClient.putLineup(createdGame.getId(), "home", lineupDefs.get(0));
        out.append(statusAfterAddingHomeLineup);

        logger.info("Retrieving game by id");
        out.append("\nRetrieving game by id\n");
        Game foundGame2 = gameClient.find(createdGame.getId());
        out.append(foundGame2.toString());

        } catch (Exception e) {
            out.append("Serdeser exception " + e);
        }

        logger.info("Start complete");
        out.append("\nReady to play!\n");
        return out.toString();
    }

    @Get("/play")
    public String play(@QueryValue Optional<String> off, @QueryValue Optional<String> def) {
        return "Not implemented yet";
    }

    @Get("/debug")
    public String debug() {
        String gameSer = gameClient.debug();
        logger.info("Serialized game =" + gameSer);
        try {
            GameTEST gt = new ObjectMapper().readValue(gameSer, GameTEST.class);
            return gt.toString();
        } catch (Exception e) {
            logger.info("Deserializing game threw", e);
            return "Deserializing \n" + gameSer + "\nThrew exception\n" + e.toString();
        }
    }

    @Get("/debugl")
    public String debugl() {
        TeamTEST t1 = new TeamTEST(12345L, 98, null);
        // TeamTEST t2 = new TeamTEST(12346L, 13, null);
        GameTEST g = new GameTEST(44121L, "gee", t1); //, t2);
        t1.setGame(g);
        //t2.setGame(g);
        try {
            String gameSer = new ObjectMapper().writeValueAsString(g);
            GameTEST g2 = new ObjectMapper().readValue(gameSer, GameTEST.class);
            return "Serialized output:\n" + gameSer + "\nReconstituted object:\n" + g2.toString();
        } catch (Exception e) {
            logger.info("Serializing/deserializing game threw", e);
            return "Serializing/deserializing \n" + g + "\nThrew exception\n" + e.toString();
        }
    }

    @Get("/ex")
    public String ex() {
        Product iphone7 = new Product(1, "Iphone 7");
        Product iPadPro = new Product(2, "IPadPro");

        List<Product> appleProducts = new ArrayList<Product>(Arrays.asList(iphone7, iPadPro));

        Company apple = new Company(1, "Apple", appleProducts);

        iphone7.setCompany(apple);
        iPadPro.setCompany(apple);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(apple);
            Company co2 = mapper.readValue(result, Company.class);
            return "Ser:\n" + result + "\nDeser:\n" + co2;
        } catch (Exception e) {
            return "Exception " + e;
        }

    }

    // Test how to get query params
    @Get("/concat")
    public String concat(@QueryValue Optional<String> s1, @QueryValue Optional<String> s2) {
        return (s1.isPresent() ? s1.get() : "Hello") + "+" + (s2.isPresent() ? s2.get() : "world") + "!";
    }

}
