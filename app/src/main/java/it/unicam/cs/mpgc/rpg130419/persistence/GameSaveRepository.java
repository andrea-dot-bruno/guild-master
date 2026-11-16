package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.GameSession;

import java.io.IOException;
import java.util.function.Supplier;

public interface GameSaveRepository {

    GameSession loadOrCreate(Supplier<GameSession> newSession) throws IOException;

    void save(GameSession session) throws IOException;
}
