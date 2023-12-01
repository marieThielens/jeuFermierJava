package eu.epfc.anc3.model;
import javafx.beans.property.*;


public abstract class Element implements Comparable<Element> {
    //ordre de priorité dans le Set de Plot
    int positionInPlot;
    // propriété observable pour savoir à quel stade "de vie" est le légume
    ObjectProperty<ElementState> stateProperty;
    // jours qui passent
    ObjectProperty<PlotValue> typeProperty ;
    // contient la durée de développement d'un objet
    IntegerProperty developmentTimeProperty;
    // les points gagnés par objet récolté
    IntegerProperty pointsProperty;
    // Le total des points gagné
    BooleanProperty isGrassProperty;
    //Présence de gazon
    VegetableState state;


    public Element() {
        // jours qui passent
        this.developmentTimeProperty = new SimpleIntegerProperty(1);
        // total des points gagné
        this.pointsProperty = new SimpleIntegerProperty(0);
        //Présence de gazon
        this.isGrassProperty = new SimpleBooleanProperty(false);
        //Element State
        this.stateProperty = new SimpleObjectProperty<>(ElementState.STATE_1) ;

    }

    @Override
    public int compareTo(Element o) {
        return this.positionInPlot - o.positionInPlot;
    }

    // State -----------------
    // définit la valeur de l'état de la plante (state) sur une nouvelle valeur donnée en argument
    void setState(VegetableState state) {
        this.state = state;
    }
    // renvoie la propriété de l'état actuel de l'élément en lecture seule
    public ReadOnlyObjectProperty<ElementState> currentStateProperty(){
        return this.stateProperty;
    }
    void setElementState(ElementState state){
        this.stateProperty.set(state);
    }

    //Grass
    public BooleanProperty isGrassProperty() {
        return isGrassProperty;
    }
    public void setIsGrassProperty(boolean isGrassProperty) {
        this.isGrassProperty.set(isGrassProperty);
    }


    //Type
    public PlotValue getTypeProperty() {
        return typeProperty.get();
    }
    PlotValue getElementType(){
        return typeProperty.get();
    }

    //Developpement Time
    public int getDevelopmentTimeProperty() {
        return developmentTimeProperty.get();
    }

    //Score
    public int getPointsProperty() {
        return pointsProperty.get();
    }



    // classe abstraite -----------------
    abstract void  grow();
    abstract void fertilize();
    abstract int harvest();
    abstract ReadOnlyIntegerProperty getDuration();
    abstract void setDuration(int duration);
    abstract ReadOnlyIntegerProperty getPoints();
    abstract void setPoints(int points);
    abstract Element copy();
    public String toString(){
        return this.typeProperty.get().toString() + this.stateProperty.get().toString();
    }

}
