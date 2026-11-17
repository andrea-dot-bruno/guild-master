package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.GameSession;

import java.io.IOException;
import java.util.Optional;

public interface GameSaveRepository {

    Optional<GameSession> load() throws IOException;

    void save(GameSession session) throws IOException;
}
