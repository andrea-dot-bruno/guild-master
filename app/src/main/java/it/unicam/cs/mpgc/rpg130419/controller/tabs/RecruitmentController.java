package it.unicam.cs.mpgc.rpg130419.controller.tabs;

import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130419.model.heroes.RecruitmentCandidate;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.function.Consumer;

public class RecruitmentController {
    private Consumer<RecruitmentCandidate> hireHero;
    private Runnable refreshRecruitmentAction;

    @FXML private TableView<RecruitmentCandidate> recruitmentCandidatesTable;
    @FXML private Button hireHeroButton;
    @FXML private Button refreshRecruitmentButton;
    @FXML private TableColumn<RecruitmentCandidate, String> candidateNameColumn;
    @FXML private TableColumn<RecruitmentCandidate, String> candidateClassColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidateLevelColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidatePayColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidateHireFeeColumn;
    @FXML private ImageView recruitHeroIcon;
    @FXML private Label recruitHeroName;
    @FXML private Label recruitHeroLevelAndClass;
    @FXML private Label recruitHeroPay;
    @FXML private Label recruitHeroHireFee;
    @FXML private ImageView recruitHeroLeftItemIcon;
    @FXML private Label recruitHeroLeftItemDamage;
    @FXML private ImageView recruitHeroRightItemIcon;
    @FXML private Label recruitHeroRightItemDamage;

    @FXML private void initialize() {
        candidateNameColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().hero().getName()));
        candidateClassColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().hero().getHeroClass()));
        candidateLevelColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hero().getLevel()));
        candidatePayColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hero().getDailyPay()));
        candidateHireFeeColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hireFee()));

        refreshRecruitmentButton.setText("Refresh Candidates (%d G)".formatted(GameSession.RECRUITMENT_REFRESH_COST));
        clearInfoFields();
        hireHeroButton.disableProperty().bind(
                recruitmentCandidatesTable.getSelectionModel().selectedItemProperty().isNull());
        recruitmentCandidatesTable.getSelectionModel().selectedItemProperty()
                .addListener((property, oldCandidate, newCandidate) -> {
                    if (newCandidate == null) {
                        clearInfoFields();
                    } else {
                        populateInfoFields(newCandidate);
                    }
                });
    }

    @FXML private void handleHireHeroButton() {
        RecruitmentCandidate candidate = recruitmentCandidatesTable.getSelectionModel().getSelectedItem();
        if (candidate != null) hireHero.accept(candidate);
    }

    @FXML private void handleRefreshRecruitment() {
        refreshRecruitmentAction.run();
    }

    public void setRefreshRecruitmentAction(Runnable refreshRecruitmentAction) {
        this.refreshRecruitmentAction = refreshRecruitmentAction;
    }

    public void setRefreshRecruitmentDisabled(boolean disabled) {
        refreshRecruitmentButton.setDisable(disabled);
    }

    public void setHireHero(Consumer<RecruitmentCandidate> hireHero) {
        this.hireHero = hireHero;
    }

    public void showHireFailure(RecruitmentCandidate candidate) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(hireHeroButton.getScene().getWindow());
        alert.setTitle("Recruitment");
        alert.setHeaderText("Unable to hire %s".formatted(candidate.hero().getName()));
        alert.setContentText("Check that the hero is still available and that you have %d gold."
                .formatted(candidate.hireFee()));
        alert.show();
    }

    public void setRecruitmentCandidates(List<RecruitmentCandidate> recruitmentCandidates) {
        TableView.TableViewSelectionModel<RecruitmentCandidate> selection = recruitmentCandidatesTable.getSelectionModel();
        RecruitmentCandidate selected = selection.getSelectedItem();
        recruitmentCandidatesTable.getItems().setAll(recruitmentCandidates);
        if (selected != null && recruitmentCandidates.contains(selected)) {
            selection.select(selected);
        } else {
            selection.clearSelection();
            selection.selectFirst();
        }
    }

    private void clearInfoFields() {
        recruitHeroName.setText("Select a Hero to display its details.");
        recruitHeroIcon.setImage(null);
        recruitHeroLevelAndClass.setText("");
        recruitHeroPay.setText("");
        recruitHeroHireFee.setText("");
        recruitHeroLeftItemIcon.setImage(null);
        recruitHeroLeftItemDamage.setText("");
        recruitHeroRightItemIcon.setImage(null);
        recruitHeroRightItemDamage.setText("");
    }

    private void populateInfoFields(RecruitmentCandidate candidate) {
        Hero<?> hero = candidate.hero();
        recruitHeroName.setText(hero.getName());
        recruitHeroIcon.setImage(new Image(hero.getIconPath()));
        recruitHeroLevelAndClass.setText("Level %d · %s".formatted(hero.getLevel(), hero.getHeroClass()));
        recruitHeroPay.setText("Daily Pay: %d G".formatted(hero.getDailyPay()));
        recruitHeroHireFee.setText("Hire Fee: %d G".formatted(candidate.hireFee()));
        recruitHeroLeftItemIcon.setImage(new Image(hero.getLeftWeapon().getIconPath()));
        recruitHeroLeftItemDamage.setText("Power: %d".formatted(hero.getLeftWeapon().getPower()));
        recruitHeroRightItemIcon.setImage(new Image(hero.getRightWeapon().getIconPath()));
        recruitHeroRightItemDamage.setText("Power: %d".formatted(hero.getRightWeapon().getPower()));
    }
}
