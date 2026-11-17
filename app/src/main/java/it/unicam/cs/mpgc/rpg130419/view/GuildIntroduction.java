package it.unicam.cs.mpgc.rpg130419.view;

import it.unicam.cs.mpgc.rpg130419.controller.GuildIntroductionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.function.Predicate;

public final class GuildIntroduction {
    private GuildIntroduction() {}

    public static Stage create(Window owner, Predicate<String> confirmName) throws IOException {
        FXMLLoader loader = new FXMLLoader(GuildIntroduction.class.getResource("/view/guild-introduction.fxml"));
        Parent content = loader.load();
        GuildIntroductionController controller = loader.getController();
        controller.setConfirmName(confirmName);

        Stage introduction = new Stage(StageStyle.UNDECORATED);
        introduction.setTitle("Welcome Back, Captain");
        introduction.initOwner(owner);
        introduction.initModality(Modality.WINDOW_MODAL);
        introduction.setResizable(false);
        introduction.setScene(new Scene(content));
        introduction.setOnCloseRequest(event -> event.consume());
        introduction.setOnShown(event -> {
            introduction.setX(owner.getX() + (owner.getWidth() - introduction.getWidth()) / 2);
            introduction.setY(owner.getY() + (owner.getHeight() - introduction.getHeight()) / 2);
        });
        return introduction;
    }
}
