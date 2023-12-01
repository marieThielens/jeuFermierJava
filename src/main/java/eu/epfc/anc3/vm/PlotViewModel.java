package eu.epfc.anc3.vm;
import eu.epfc.anc3.model.*;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;

public class PlotViewModel {
    private final int line, col;
    private final GameFacade game;
    public PlotViewModel(int line, int col, GameFacade game) {
        this.line = line;
        this.col = col;
        this.game = game;
    }

    public ObservableSet<Element> elementsInThePlotProperty(){
        return(game.elementsInThePlotProperty(line,col));
    }
    public void teleportationFarmer(){
        game.teleportationFarmer(line,col);
    }
    public ReadOnlyBooleanProperty hasFarmer(){
        return game.hasFarmer(line,col);
    }

}
