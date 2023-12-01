package eu.epfc.anc3.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Soil extends Element{

    Soil(){
        this.typeProperty = new SimpleObjectProperty<>(PlotValue.SOIL);
        this.stateProperty = new SimpleObjectProperty<>(ElementState.STATE_1);
        this.positionInPlot = 0;
    }
    @Override
    void grow() { }
    @Override
    void fertilize() {}

    @Override
    int harvest() {
        return 0;
    }

    @Override
    ReadOnlyIntegerProperty getDuration() {
        return null;
    }

    @Override
    void setDuration(int duration) {

    }

    @Override
    ReadOnlyIntegerProperty getPoints() {
        return null;
    }

    @Override
    void setPoints(int points) {

    }

    @Override
    public Element copy() {
        Element e = new Soil();
        e.developmentTimeProperty.set(this.developmentTimeProperty.get());
        e.pointsProperty.setValue(this.pointsProperty.getValue());
        // total des points gagné
        e.pointsProperty.set(this.pointsProperty.get());
        //Présence de gazon
        e.isGrassProperty.set(this.isGrassProperty.getValue());
        //Element State
        e.stateProperty.set(this.stateProperty.get());
        return e;
    }
}
