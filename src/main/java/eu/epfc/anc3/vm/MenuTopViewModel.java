package eu.epfc.anc3.vm;

import eu.epfc.anc3.model.GameFacade;
import javafx.beans.property.*;

public class MenuTopViewModel {
    private final GameFacade game;

    public MenuTopViewModel(GameFacade game){
        this.game = game;
    }

    public ReadOnlyStringProperty scoreLabelProprety(){return new SimpleStringProperty("Score : ");}
    public IntegerProperty getScore(){
        return game.getScore();
    }
    public ReadOnlyStringProperty nbJourLabelProperty() { return new SimpleStringProperty("Jour : ");}
    public  IntegerProperty getNbJour() { return game.getNbJour(); }

}
