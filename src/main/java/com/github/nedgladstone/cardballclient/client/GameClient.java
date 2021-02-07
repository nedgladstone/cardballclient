package com.github.nedgladstone.cardballclient.client;

import com.github.nedgladstone.cardballclient.model.*;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

import java.sql.Timestamp;
import java.util.stream.Collectors;

@Client("http://localhost:8082/player")
public interface GameClient {
    @Get
    Iterable<Game> list();

    @Get("/{id}")
    Game find(long id);

    @Post
    Game create(GameDefinition definition);

    @Get("/{id}/status")
    GameStatus findStatus(long id);

    @Get("/{gameId}/lineup")
    LineupsResponse listLineups(long gameId);

    @Put("/{gameId}/lineup/{side}")
    GameStatus putLineup(long gameId, String side, LineupDefinition lineupDefinition);

    @Post("/{gameId}/strategy/{role}")
    GameStatus postStrategy(long gameId, String role, String strategy);
}
