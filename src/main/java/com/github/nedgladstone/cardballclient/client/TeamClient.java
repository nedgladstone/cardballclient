package com.github.nedgladstone.cardballclient.client;

import com.github.nedgladstone.cardballclient.model.Player;
import com.github.nedgladstone.cardballclient.model.Team;
import com.github.nedgladstone.cardballclient.model.TeamDefinition;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8082/team")
public interface TeamClient {
    @Get
    Iterable<Team> list();

    @Get("/{id}")
    Team find(long id);

    @Post
    Team create(TeamDefinition definition);

    @Post("/{teamId}/player")
    Team addPlayer(long teamId, Player player);
}
