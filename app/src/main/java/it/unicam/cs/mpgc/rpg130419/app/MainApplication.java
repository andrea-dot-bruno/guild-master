package it.unicam.cs.mpgc.rpg130419.app;

import it.unicam.cs.mpgc.rpg130419.controller.MainController;
import it.unicam.cs.mpgc.rpg130419.controller.StartScreenController;
import it.unicam.cs.mpgc.rpg130419.model.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Guild;
import it.unicam.cs.mpgc.rpg130419.model.Knight;
import it.unicam.cs.mpgc.rpg130419.model.Mage;
import it.unicam.cs.mpgc.rpg130419.model.RecruitmentCandidate;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Sword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Wand;
import it.unicam.cs.mpgc.rpg130419.persistence.GameSaveRepository;
import it.unicam.cs.mpgc.rpg130419.persistence.JsonGameSaveRepository;
import it.unicam.cs.mpgc.rpg130419.view.DayEndDialogs;
import it.unicam.cs.mpgc.rpg130419.view.SaveDialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Path saveFile = Path.of("saves", "save.json");
        GameSaveRepository saves = new JsonGameSaveRepository(saveFile);
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/view/start-screen.fxml")
        );
        Parent root = loader.load();
        StartScreenController controller = loader.getController();
        controller.setNewGameAction(() -> {
            if (SaveDialogs.confirmOverwrite(stage)) startNewGame(stage, saves);
        });
        controller.setLoadGameAction(() -> loadGame(stage, saves));

        stage.setTitle("Guild Master");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadGame(Stage stage, GameSaveRepository saves) {
        GameSession session;
        try {
            session = saves.loadOrCreate(this::newSession);
            stage.getScene().setRoot(createGameView(session, saves));
        } catch (IOException exception) {
            SaveDialogs.showError(stage, "Could Not Open Game", exception);
            return;
        }
        if (session.isGameOver() && DayEndDialogs.confirmNewGame(stage)) {
            startNewGame(stage, saves);
        }
    }

    private Parent createGameView(GameSession session, GameSaveRepository saves) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/view/main-page.fxml")
        );
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.linkSession(session, saves);
        controller.updateDisplay();
        return root;
    }

    private void startNewGame(Stage stage, GameSaveRepository saves) {
        GameSession session = newSession();
        try {
            Parent root = createGameView(session, saves);
            saves.save(session);
            stage.getScene().setRoot(root);
        } catch (IOException exception) {
            SaveDialogs.showError(stage, "Could Not Start New Game", exception);
        }
    }

    private GameSession newSession() {
        Guild guild = new Guild("Adventurers' Guild");
        GameSession session = new GameSession(guild, 1, false, List.of(), List.of());
        session.addMoney(150);

        Knight bram = new Knight("Bram", "/assets/knight-icons/bram.png");
        bram.setRightWeapon(new Sword());
        Mage lyra = new Mage("Lyra", "/assets/mage-icons/lyra.png");
        lyra.setRightWeapon(new Wand());
        session.addHero(bram);
        session.addHero(lyra);

        Dungeon firstDungeon = new Dungeon("Mossroot Warren", "/assets/dungeon-icons/mossroot.png", 8, 1, 50, 100);
        session.launchExpedition(new Expedition(firstDungeon, List.of(bram)));
        session.addAvailableDungeon(new Dungeon("Old King's Road", "/assets/dungeon-icons/trail.png", 8, 1, 50, 100));
        session.addAvailableDungeon(new Dungeon("Briarwatch Ruins", "/assets/dungeon-icons/ruins.png", 16, 2, 100, 140));

        Knight gareth = new Knight("Gareth", "/assets/knight-icons/cedric.png");
        gareth.setRightWeapon(new Sword());
        Mage iris = new Mage("Iris", "/assets/mage-icons/mira.png");
        iris.setRightWeapon(new Wand());
        session.addRecruitmentCandidate(new RecruitmentCandidate(gareth, 60));
        session.addRecruitmentCandidate(new RecruitmentCandidate(iris, 50));
        return session;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
