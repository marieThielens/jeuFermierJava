package eu.epfc.anc3.view;

import eu.epfc.anc3.vm.MenuTopViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class MenuTopView extends HBox {
    // Score ---------
    private final Label Score = new Label(); // le texte
    private final Label labelScore = new Label(); // les chiffres
    private final StringProperty score = new SimpleStringProperty();

    // Jour ---------
    private final Label labelNbJour = new Label();
    private final Label labelNumberNbJour = new Label();
    private final StringProperty nbJour = new SimpleStringProperty();

    private final MenuTopViewModel menuTopViewModel;

    public MenuTopView(MenuTopViewModel menuTopViewModel){
        this.menuTopViewModel = menuTopViewModel;
        configBiding();
        configMenu();
        setAlignment(Pos.BOTTOM_CENTER);
    }

    private void configBiding(){
        score.bind(Bindings.convert(menuTopViewModel.getScore()));
        labelScore.textProperty().bind(score);

        nbJour.bind(Bindings.convert(menuTopViewModel.getNbJour()));
        labelNumberNbJour.textProperty().bind(nbJour);
    }

    private void configMenu() {
        getChildren().addAll(Score, labelScore, labelNbJour, labelNumberNbJour);
        HBox.setMargin(labelScore, new Insets(0, 40, 0, 0));
        configLabels(); // le texte des boutons
    }

    private void configLabels() { // le texte de mes boutons
        Score.textProperty().bind(menuTopViewModel.scoreLabelProprety());
        labelNbJour.textProperty().bind(menuTopViewModel.nbJourLabelProperty());
    }
}
