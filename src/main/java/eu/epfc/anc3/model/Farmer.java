package eu.epfc.anc3.model;

import javafx.beans.property.*;
import javafx.scene.input.KeyCode;

import static eu.epfc.anc3.model.Game.getScoreProperty;
import static eu.epfc.anc3.model.Game.setScore;


public class Farmer {
    private Land land;
    private final ObjectProperty<Plot> positionFarmer = new SimpleObjectProperty<>();
    private final ObjectProperty<FarmerAction> actionFarmer = new SimpleObjectProperty<>();
    private int currentRow;
    private int currentCol;

    // constructeur, crée un nouveau personnage à partir d'une land
    public Farmer(Land land){
        this.land = land;
        // met le fermier sur la 1ere case
        updatePlotFarmer(0,0);
    }

    private Farmer(){}


    //MOUVEMENT ----------------------------------

    // met à jour la position du fermier sur la parcelle en fonction de sa position actuelle
    private void updatePlotFarmer(int line, int col){
        positionFarmer.setValue(land.playFarmer(line,col));
    }
    private void removePlotFarmer(int line, int col){
        positionFarmer.setValue(land.playFarmer(line,col));
    }

    // quand on redémarre le jeu
    void restart(){
        // supprime la position actuelle du fermier
        this.removePlotFarmer(currentRow,currentCol);
        // mets à jour sa position sur la 1ere case
        this.updatePlotFarmer(0,0);
        // le téléporte sur la 1ere case
        this.teleportationFarmer(0,0);
    }

    void teleportationFarmer(int line, int col){
        removePlotFarmer(currentRow, currentCol);
        currentCol = col;
        currentRow = line;
        updatePlotFarmer(currentRow, currentCol);
    }

     void moveFarmer(KeyCode direction){

        switch (direction) {
            case LEFT:
                if (currentRow > 0) {
                    removePlotFarmer(currentRow, currentCol);
                    currentRow--;
                    updatePlotFarmer(currentRow, currentCol);
                }
                break;
            case RIGHT:
                if (currentRow < Land.PLOT_WIDTH - 1) {
                    removePlotFarmer(currentRow, currentCol);
                    currentRow++;
                    updatePlotFarmer(currentRow, currentCol);
                }
                break;
            case UP:
                if (currentCol > 0) {
                    removePlotFarmer(currentRow, currentCol);
                    currentCol--;
                    updatePlotFarmer(currentRow, currentCol);
                }
                break;
            case DOWN:
                    if (currentCol < Land.PLOT_HEIGHT - 1) {
                        removePlotFarmer(currentRow, currentCol);
                        currentCol++;
                        updatePlotFarmer(currentRow, currentCol);
                    }
                    break;
            default:
                break;
        }
    }

    //ACTION -----------------------------------
    // planter un élément sur la parcelle actuelle du fermier. Plotvalue représente l'élément à planter
    void plant(PlotValue value){
        switch (value){
            case CABBAGE -> land.plantElement(currentRow,currentCol,PlotValue.CABBAGE);
            case CARROT -> land.plantElement(currentRow,currentCol,PlotValue.CARROT);
            case GRASS -> land.plantElement(currentRow,currentCol,PlotValue.GRASS);
        }
    }

    // retourne la valeur du nombre de point gagnés pour la récolte du terrain
    int harvest(){
        return land.removeElement(currentRow,currentCol);
    }
    void fertelize(){
        land.fertelize(currentRow,currentCol);
    }

    // getters and setters ---

    // retourne l'action actuellement définie pour le fermier
     FarmerAction getActionFarmer() {
        return actionFarmer.get();
    }
    // Dit qu'elle est l'action
     void setActionFarmer(FarmerAction actionFarmer) {
        this.actionFarmer.set(actionFarmer);
    }
    // dit quel est l'action en particulier et l'envoie à la méthode précédente
    void setAction(FarmerAction action){
        switch (action){
            case PLANTGRASS -> this.setActionFarmer(FarmerAction.PLANTGRASS);
            case PLANTCABBAGE -> this.setActionFarmer(FarmerAction.PLANTCABBAGE);
            case PLANTCARROT ->  this.setActionFarmer(FarmerAction.PLANTCARROT);
            case FERTELIZE -> this.setActionFarmer(FarmerAction.FERTELIZE);
            case HARVEST -> this.setActionFarmer(FarmerAction.HARVEST);
        }
    }

    void action(){

        if(this.getActionFarmer() == FarmerAction.PLANTCARROT){

            this.plant(PlotValue.CARROT);
        }
        if(this.getActionFarmer() == FarmerAction.PLANTCABBAGE){

            this.plant(PlotValue.CABBAGE);
        }
        if(this.getActionFarmer() == FarmerAction.PLANTGRASS){

            this.plant(PlotValue.GRASS);
        }
        if(this.getActionFarmer() == FarmerAction.FERTELIZE){

            this.fertelize();

        }
        if(this.getActionFarmer() == FarmerAction.HARVEST){

            int scoreOfPlot = this.harvest();
            // score existant plus le nouveau . Avant ça s'écrasait
            setScore(getScoreProperty().get() + scoreOfPlot);
        }

    }

    //MEMENTO -----------------------
     Farmer copy() {
        Farmer clone = new Farmer();
        clone.currentRow = this.currentRow;
        clone.currentCol = this.currentCol;
        clone.land = this.land;
        return clone;
    }

     void restore(Farmer farmer){
        this.removePlotFarmer(currentRow,currentCol);
        this.teleportationFarmer(farmer.currentRow,farmer.currentCol);
    }
}
