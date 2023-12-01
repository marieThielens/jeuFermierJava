package eu.epfc.anc3.vm;

import eu.epfc.anc3.model.GameFacade;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuRightViewModel {
    private final GameFacade game;
    public BooleanProperty isRunningRight = new SimpleBooleanProperty(false);

    public MenuRightViewModel(GameFacade game, MenuBottomViewModel menuBottomViewModel) {
        this.game = game;
        isRunningRight.bind(menuBottomViewModel.isRunning);
    }

    // Texte de mes boutons -------------
    public StringProperty grassLabelProperty() { return new SimpleStringProperty("Planter du gazon"); }
    public StringProperty carotsLabelProperty() {return new SimpleStringProperty("Planter des carottes"); }
    public StringProperty cabbageLabelProperty() { return new SimpleStringProperty("Planter des choux");}
    public StringProperty fertilizedLabelPropertry() { return new SimpleStringProperty("Fertiliser");}
    public StringProperty harvestLabelProperty() { return new SimpleStringProperty("Récolter");}

    // binding des boutons pour les activer / désacitver ------
    private final BooleanProperty boutonsDesactives = new SimpleBooleanProperty(true);
    public BooleanProperty boutonsDesactivesProperty() {
        return boutonsDesactives;
    }
    public void setBoutonsDesactives(boolean value) {
        boutonsDesactives.set(value);
    }

    public void plantGrass(){
        game.plantGrass();
    }
    public void plantCabbage(){ game.plantCabbage(); }
    public void plantCarrot(){ game.plantCarrot(); }
    public void fertilize(){game.fertilize();}
    public void harvest(){game.harvest();}



}
