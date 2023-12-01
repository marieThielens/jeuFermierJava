package eu.epfc.anc3.model;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Grass extends Element{

    public Grass(){
        // on itinialise la propriété à l'herbe
        this.typeProperty = new  SimpleObjectProperty<>(PlotValue.GRASS);
        this.stateProperty = new SimpleObjectProperty<>(ElementState.STATE_1);
        // la "vie" qu'il a vécu
        this.developmentTimeProperty = new SimpleIntegerProperty(0);
        // pour le respect d'avoir bien l'ordre des éléments
        this.positionInPlot = 1;
    }

    @Override
    public Element copy(){
        Element e = new Grass();
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

    @Override
    void grow() {
        setDuration(getDuration().get()+1);
        if(getDuration().get()==12){
            this.stateProperty.set(ElementState.DISAPEAR);
        }
    }
    @Override
    void fertilize() {}

    @Override
    int harvest() {
        return 0;
    }

    public ReadOnlyIntegerProperty getDuration(){
        return this.developmentTimeProperty;
    }

    @Override
    void setDuration(int duration) {
        this.developmentTimeProperty.set(duration);
    }

    @Override
    ReadOnlyIntegerProperty getPoints() {
        return pointsProperty;
    }

    @Override
    void setPoints(int points) {}
}
