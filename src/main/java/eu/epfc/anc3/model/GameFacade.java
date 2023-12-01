package eu.epfc.anc3.model;

import javafx.beans.property.*;

import javafx.collections.ObservableSet;
import javafx.scene.input.KeyCode;

public class GameFacade {
    // instancier la classe principale du jeu
    private final Game game = new Game();

    //FARMER ----------------------------------
    public ReadOnlyBooleanProperty hasFarmer(int line, int col){
        return game.getHasFarmer(line,col);
    }
    // Déplacer le fermier vers une nouvelle position
    public void teleportationFarmer(int line, int col){game.teleportationFarmer(line,col);}
    // Déplacer le fermier selon les touches, direction
    public void moveFarmer(KeyCode direction){game.moveFarmer(direction);}
    // une méthode qui effectue l'action du Farmer sur la parcelle courante.
    public void farmerAction(){game.farmerAction();}

    //ACTION IN GAME --------------------------------------
    // demande au fermier de planter un chou sur la parcelle courante
    public void plantCabbage(){
        game.setFarmerAction(FarmerAction.PLANTCABBAGE);
    }
    public void plantCarrot(){
        game.setFarmerAction(FarmerAction.PLANTCARROT);
    }
    public void fertilize(){game.setFarmerAction(FarmerAction.FERTELIZE);}
    public void harvest(){game.setFarmerAction(FarmerAction.HARVEST);}
    public void plantGrass(){game.setFarmerAction(FarmerAction.PLANTGRASS);}

    //ACTION ON GAME ---------------------------------------
    public void start(){game.start();} // Démarrer le jeu
    public void stop() {game.stop();}
    public void addNewDay(){game.addNewDay();}
    public void saveGame() {game.saveGame();}
    public void restore(){game.restoreMemento();}

    // retourne une liste observable des éléments présents dans la parcelle spécifiée
    public ObservableSet<Element> elementsInThePlotProperty(int line, int col){
        return game.elementsInThePlotProperty(line,col);
    }
    // Score actuel du jeu
    public IntegerProperty getScore(){return Game.getScoreProperty();}
    public IntegerProperty getNbJour() {return  game.getNbJourProperty();}
    // booleen avec l'état actuel du jeu
    public BooleanProperty isStartedProperty() { return game.getIsStarted() ;}
    public BooleanProperty getisGameSavedProperty(){return game.getisGameSavedProperty();}

    //TERRAIN----------------------------------
    // donne la largeur et la hauteur du terrain
    public static int landWidth() { return Land.PLOT_WIDTH; }
    public static int landHeight() { return Land.PLOT_HEIGHT; }

}
