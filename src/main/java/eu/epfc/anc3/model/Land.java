package eu.epfc.anc3.model;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;

import java.util.Arrays;

public class Land {

    public static final int PLOT_WIDTH = 25;
    public static final int PLOT_HEIGHT = 15;
    // Les parcelles. Chaque parcelle contient des elements et peut être fertilisée
    private Plot[][] matrix;

    // constructeur qui initialise les parcelles et rempli chaque parcelle
    // avec une instance de la classe Plot
    Land() {
        matrix = new Plot[PLOT_WIDTH][];
        for (int i = 0; i < PLOT_WIDTH; ++i) {
            matrix[i] = new Plot[PLOT_HEIGHT];
            for (int j = 0; j < PLOT_HEIGHT; ++j) {
                matrix[i][j] = new Plot();
            }
        }
    }

    // Réintialise toutes les parcelles et met le fermier sur la 1ere case
     void restartLand() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = new Plot();
                if(i == 0 && j == 0){
                    this.matrix[i][j].setFamer();
                }
            }
        }
    }

    // faire grandir tous les éléments dans toutes les parcelles et retourne
    // le nombre de point perdus par rapport à la durée de vie de l'élément
    int IncrementVegetableDuration(){
        int lostPoints = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                lostPoints += this.matrix[i][j].growElements();
            }
        }
        return lostPoints;
    }

    // Crée une copie des parcelles
     Land copy() {
            Land landClone = new Land();
            landClone.matrix = new Plot[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    landClone.matrix[i][j] = matrix[i][j].copy();
                }
            }
            return landClone;
    }
    // Restaure les parcelles à partir de la copie des parcelles
    void restore(Land landcopy){
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j].restore(landcopy.matrix[i][j].copy());
            }
        }
    }

    // Retourne une "liste" des éléments qui se trouvent dans la parcelle
    ObservableSet<Element> getelementsInThePlotProperty(int line, int col){
        return matrix[line][col].getElementsInThePlot();
     }

     // Vérifie si le fermier est dans la parcelle
    ReadOnlyBooleanProperty hasFarmer(int line, int col){
        return matrix[line][col].getHasFarmer();
    }
    // fertilise la parcelle choisie
    void fertelize(int line, int col){
        matrix[line][col].fertilize();
    }

    int removeElement(int line, int col){
        return matrix[line][col].harvestElement();
    }
    void plantElement(int line, int col, PlotValue value){
        matrix[line][col].plantElement(value);
    }

    Plot playFarmer(int line, int col) {
        matrix[line][col].setFamer();
        return matrix[line][col];
    }

}
