package it.unicam.cs.mpgc.rpg130419.app;

import it.unicam.cs.mpgc.rpg130419.controller.MainController;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/view/guild-master-ui-diy.fxml")
        );
        Parent root = loader.load();

        MainController controller = loader.getController();
        GameSession session = new GameSession();
        controller.linkSession(session);
        session.addMoney(120);
        controller.updateDisplay();


        stage.setTitle("Guild Master");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
