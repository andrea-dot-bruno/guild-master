package it.unicam.cs.mpgc.rpg130419.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.function.Supplier;

public final class JsonGameSaveRepository implements GameSaveRepository {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().setStrictness(Strictness.STRICT).create();
    private final Path saveFile;

    public JsonGameSaveRepository(Path saveFile) {
        this.saveFile = saveFile.toAbsolutePath();
    }

    @Override
    public GameSession loadOrCreate(Supplier<GameSession> newSession) throws IOException {
        String json;
        try {
            json = Files.readString(saveFile);
        } catch (NoSuchFileException exception) {
            return createAndSave(newSession);
        }

        if (json.isBlank()) return createAndSave(newSession);

        try {
            GameSave save = gson.fromJson(json, GameSave.class);
            if (save == null) throw new IllegalArgumentException("Expected a JSON save object.");
            return save.restore();
        } catch (JsonParseException | IllegalArgumentException | NullPointerException exception) {
            throw new IOException("Invalid game save: " + saveFile, exception);
        }
    }

    private GameSession createAndSave(Supplier<GameSession> newSession) throws IOException {
        GameSession session = Objects.requireNonNull(newSession.get(), "Expected a new game session.");
        save(session);
        return session;
    }

    @Override
    public void save(GameSession session) throws IOException {
        String json;
        try {
            json = gson.toJson(GameSave.from(session));
        } catch (JsonParseException | IllegalArgumentException exception) {
            throw new IOException("Could not encode game save: " + saveFile, exception);
        }

        Path directory = Files.createDirectories(saveFile.getParent());
        Path temporaryFile = Files.createTempFile(directory, "guild-master-", ".tmp");
        try {
            Files.writeString(temporaryFile, json);
            Files.move(temporaryFile, saveFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(temporaryFile);
        }
    }
}
