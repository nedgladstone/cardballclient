package com.github.nedgladstone.cardballclient.client;

import com.github.nedgladstone.cardballclient.model.Player;
import com.github.nedgladstone.cardballclient.model.PlayerDefinition;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8082/player")
public interface PlayerClient {
    @Get
    Iterable<Player> list();

    @Get("/{id}")
    Player find(long id);

    @Put
    Player create(PlayerDefinition definition);
}
