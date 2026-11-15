package it.unicam.cs.mpgc.rpg130419.app;

import it.unicam.cs.mpgc.rpg130419.controller.MainController;
import it.unicam.cs.mpgc.rpg130419.model.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.ExpeditionState;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Knight;
import it.unicam.cs.mpgc.rpg130419.model.Mage;
import it.unicam.cs.mpgc.rpg130419.model.RecruitmentCandidate;
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
        controller.populateActiveExpeditionsTable();


        stage.setTitle("Guild Master");
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Temporary dummy data for testing the tabs. Remove this method with its call in start().
    private void addDummyData(GameSession session) {
        session.addMoney(1200);

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

        // Dungeons on the expedition board (name, difficulty, days, XP reward, gold reward).
        session.addAvailableDungeon(new Dungeon("Dungeon 4", "assets/dungeon-icons/mossroot.png", 8, 1, 50, 100));
        session.addAvailableDungeon(new Dungeon("Dungeon 5", "assets/dungeon-icons/ruins.png", 16, 2, 100, 100));
        session.addAvailableDungeon(new Dungeon("Dungeon 6", "assets/dungeon-icons/trail.png", 28, 4, 180, 100));

        // Active expeditions with separate parties and different days remaining.
        Expedition firstExpedition = new Expedition(new Dungeon("Dungeon 1", "assets/dungeon-icons/trail.png", 12, 1, 80, 120), List.of(john));
        firstExpedition.setState(ExpeditionState.ACTIVE);
        session.launchExpedition(firstExpedition);

        Expedition secondExpedition = new Expedition(new Dungeon("Dungeon 2", "assets/dungeon-icons/watchtower.png", 20, 3, 140, 120), List.of(bram, lyra));
        secondExpedition.setState(ExpeditionState.ACTIVE);
        session.launchExpedition(secondExpedition);

        // Candidates on the recruitment board with sample hire fees.
        session.addRecruitmentCandidate(new RecruitmentCandidate(new Knight("Gareth"), 60));
        session.addRecruitmentCandidate(new RecruitmentCandidate(new Mage("Iris"), 50));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
