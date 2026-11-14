package it.unicam.cs.mpgc.rpg130419.app;

import it.unicam.cs.mpgc.rpg130419.controller.MainController;
import it.unicam.cs.mpgc.rpg130419.model.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.ExpeditionState;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Knight;
import it.unicam.cs.mpgc.rpg130419.model.Mage;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Sword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Wand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/view/main-page.fxml")
        );
        Parent root = loader.load();
        MainController controller = loader.getController();
        GameSession session = new GameSession();

        // TODO: Remove this call when real game data is available.
        addDummyData(session);

        controller.linkSession(session);
        controller.updateDisplay();
        controller.populateHeroesTable();


        stage.setTitle("Guild Master");
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Temporary dummy data for testing the tabs. Remove this method with its call in start().
    private void addDummyData(GameSession session) {
        session.addMoney(120);

        // Recruited heroes with different levels, equipment and availability.
        Knight john = new Knight("John");
        Mage morris = new Mage("Morris");
        Knight bram = new Knight("Bram");
        Mage lyra = new Mage("Lyra");
        Knight rowan = new Knight("Rowan");

        john.setRightWeapon(new Sword());
        bram.addExp(160);
        bram.levelUp();
        lyra.setRightWeapon(new Wand());
        lyra.addExp(75);

        session.addHero(john);
        session.addHero(morris);
        session.addHero(bram);
        session.addHero(lyra);
        session.addHero(rowan);

        // Dungeons on the expedition board (difficulty, XP reward).
        session.getAvailableDungeons().addAll(List.of(
                new Dungeon(8, 50),
                new Dungeon(16, 100),
                new Dungeon(28, 180)
        ));

        // Active expeditions with separate parties and different days remaining.
        Expedition firstExpedition = new Expedition(new Dungeon(12, 80), List.of(john), 1);
        firstExpedition.setState(ExpeditionState.ACTIVE);
        session.launchExpedition(firstExpedition);

        Expedition secondExpedition = new Expedition(new Dungeon(20, 140), List.of(bram, lyra), 3);
        secondExpedition.setState(ExpeditionState.ACTIVE);
        session.launchExpedition(secondExpedition);

        // Candidates on the recruitment board.
        session.getRecruitmentCandidates().addAll(List.of(new Knight("Gareth"), new Mage("Iris")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
