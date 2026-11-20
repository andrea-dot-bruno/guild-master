package it.unicam.cs.mpgc.rpg130419.controller.dialogs;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ExpeditionPreparationController {
    private final List<Hero<?>> selectedHeroes = new ArrayList<>();
    private final NumberFormat probabilityFormat = NumberFormat.getPercentInstance();
    private Dungeon dungeon;
    private Predicate<List<Hero<?>>> launchExpedition;

    @FXML private Label dungeonName;
    @FXML private ImageView dungeonIcon;
    @FXML private Label dungeonDifficulty;
    @FXML private Label dungeonGoldReward;
    @FXML private Label dungeonExpReward;
    @FXML private ProgressBar probabilityBar;
    @FXML private Label probabilityLabel;
    @FXML private VBox heroCards;
    @FXML private Label launchError;
    @FXML private Button launchButton;

    public void prepare(Dungeon dungeon, List<Hero<?>> availableHeroes,
                        Predicate<List<Hero<?>>> launchExpedition) {
        this.dungeon = dungeon;
        this.launchExpedition = launchExpedition;
        probabilityFormat.setMaximumFractionDigits(1);
        probabilityFormat.setRoundingMode(RoundingMode.DOWN);

        dungeonName.setText(dungeon.getName());
        dungeonIcon.setImage(new Image(dungeon.getIconPath()));
        dungeonDifficulty.setText("Difficulty: %s".formatted(dungeon.getDifficulty()));
        dungeonGoldReward.setText("Gold Reward: %d".formatted(dungeon.getGoldReward()));
        dungeonExpReward.setText("XP Reward: %d".formatted(dungeon.getExpReward()));

        if (availableHeroes.isEmpty()) {
            heroCards.getChildren().add(new Label("No heroes available."));
        }
        for (Hero<?> hero : availableHeroes) {
            heroCards.getChildren().add(createHeroCard(hero));
        }
        updateProbability();
    }

    private ToggleButton createHeroCard(Hero<?> hero) {
        ImageView portrait = new ImageView(new Image(hero.getIconPath()));
        portrait.setFitWidth(46);
        portrait.setFitHeight(46);
        portrait.setPreserveRatio(true);

        Label name = new Label(hero.getName());
        name.getStyleClass().add("preparation-hero-name");
        String power = NumberFormat.getNumberInstance().format(hero.getTotalPower());
        Label stats = new Label("Level %d · Power %s".formatted(hero.getLevel(), power));
        stats.getStyleClass().add("preparation-hero-level");
        HBox details = new HBox(12, portrait, new VBox(3, name, stats));
        details.setAlignment(Pos.CENTER_LEFT);

        ToggleButton card = new ToggleButton();
        card.getStyleClass().add("preparation-hero-card");
        card.setGraphic(details);
        card.setAccessibleText(hero.getName() + ", " + stats.getText());
        card.setMaxWidth(Double.MAX_VALUE);
        card.selectedProperty().addListener((property, previouslySelected, selected) -> {
            if (selected) {
                selectedHeroes.add(hero);
            } else {
                selectedHeroes.remove(hero);
            }
            updateProbability();
        });
        return card;
    }

    private void updateProbability() {
        double probability = Expedition.calculateSuccessProbability(dungeon, selectedHeroes);
        probabilityBar.setProgress(probability);
        probabilityLabel.setText(probabilityFormat.format(probability));
        launchButton.setDisable(selectedHeroes.isEmpty());
    }

    @FXML private void handleLaunch() {
        if (launchExpedition.test(List.copyOf(selectedHeroes))) {
            handleCancel();
            return;
        }
        launchError.setVisible(true);
    }

    @FXML private void handleCancel() {
        launchButton.getScene().getWindow().hide();
    }
}
