package eu.epfc.anc3.model;

import javafx.beans.property.*;

public class Cabbage extends Element {
    /*private VegetableState state;*/
    final static int MAXIMUM_YIELD = 200;
    final static int STADE_1 = 5;
    final static int STADE_2 = 9;
    final static int STADE_3 = 12;
    final static int STADE_4 = 14;
    final static int STADE_LOST = 24;

    private BooleanProperty is_grass ;

    public Cabbage(){
        //Plot actuelle
        //this.currentPlot = new SimpleObjectProperty<>(plot); ;
        /*this.stateProperty = new SimpleObjectProperty<>(ElementState.STATE_1) ;*/
        this.typeProperty = new SimpleObjectProperty<>(PlotValue.CABBAGE);
        //this.is_grass = new SimpleBooleanProperty(false);
        this.state = new State1();
        this.positionInPlot = 2;
    }

    /*private void setState(VegetableState state) {
        this.state = state;
    }*/
    public void setIs_grass(boolean is_grass) {
        this.setIsGrassProperty(is_grass);
        //this.is_grass.set(is_grass);
    }

    /*private void setElementState(ElementState state){
        this.stateProperty.set(state);
    }*/
    public ObjectProperty<ElementState> getElementStateProperty(){return this.stateProperty;}

    // TODO a mettre dans Element
    void grow() {
        setDuration(getDuration().get() + 1);
        state.grow(this);
    }
    void fertilize() { state.fertilize(this); }

    @Override
    int harvest() {
        return state.harvest(this);
    }

    ReadOnlyIntegerProperty getPoints(){
        return this.pointsProperty;
    }
    void setPoints(int points){
        this.pointsProperty.setValue(points);
    }

    ReadOnlyIntegerProperty getDuration() {
        return developmentTimeProperty;
    }
    void setDuration(int developmentTime) {
        this.developmentTimeProperty.setValue(developmentTime);
    }

    @Override
    public Element copy() {
        Element e = new Cabbage();
        e.developmentTimeProperty.set(this.developmentTimeProperty.get());
        e.pointsProperty.setValue(this.pointsProperty.getValue());
        // total des points gagné
        e.pointsProperty.set(this.pointsProperty.get());
        //Présence de gazon
        e.isGrassProperty.set(this.isGrassProperty.getValue());
        //Element State
        e.stateProperty.set(this.stateProperty.get());
        e.state = this.state.copy();
        return e;
    }


    private class State1 implements VegetableState {
        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            setPoints(0);
            return getPointsProperty();
        }

        @Override
        public VegetableState copy() {
            return new State1();
        }

        @Override
        public void grow(Element element) {
            if(element.isGrassProperty().get()){
                if (element.getDuration().get() > 4 ) {
                    element.setState(new Cabbage.State2());
                    element.setElementState(ElementState.STATE_2);
                }
            }
            else if (element.getDuration().get() > STADE_1 ) {
                element.setState(new Cabbage.State2());
                element.setElementState(ElementState.STATE_2);
            }
        }
    }
    private class State2 implements VegetableState {
        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            setPoints(0);
            return getPointsProperty();
        }

        @Override
        public VegetableState copy() {
            return new State2();
        }

        @Override
        public void grow(Element element) {
            if(element.isGrassProperty().get()){
                if (element.getDuration().get() > 7 ) {
                    element.setState(new Cabbage.State3());
                    element.setElementState(ElementState.STATE_3);
                }
            }
            else if (element.getDuration().get() > STADE_2 ) {
                element.setState(new Cabbage.State3());
                element.setElementState(ElementState.STATE_3);
            }
        }
    }

    private class State3 implements VegetableState {
        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            setPoints((MAXIMUM_YIELD/4)*3);
            return getPointsProperty();
        }

        @Override
        public VegetableState copy() {
            return new State3();
        }

        @Override
        public void grow(Element element) {
            if(element.isGrassProperty().get()){
                if (element.getDuration().get() > 9 ) {
                    element.setState(new Cabbage.State4());
                    element.setElementState(ElementState.STATE_4);
                }
            }
            else if (element.getDuration().get() > STADE_3) {
                element.setState(new Cabbage.State4());
                element.setElementState(ElementState.STATE_4);
                //updateScore();
            }
        }
    }

    private class State4 implements VegetableState {
        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            setPoints(MAXIMUM_YIELD);
            return getPointsProperty();
        }

        @Override
        public VegetableState copy() {
            return new State4();
        }

        @Override
        public void grow(Element element) {
            if(isGrassProperty().get()){
                if (element.getDuration().get() > 10 ) {
                    element.setState(new Cabbage.Rotten());
                    element.setElementState(ElementState.POURRI);
                }
            }
            else if (element.getDuration().get() > STADE_4) {
                element.setState(new Cabbage.Rotten());
                element.setElementState(ElementState.POURRI);
                //updateScore();
            }
        }
    }

    private class Rotten implements VegetableState {
        //private Rotten(){ updateScore();}
        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            int daysRotten = 0;
            if(is_grass != null && is_grass.getValue()){
                daysRotten = getDuration().get() - 10;
            }
            else {daysRotten = getDuration().get() - 14;}
            int pointsLost = (int) Math.ceil(0.1 * MAXIMUM_YIELD * daysRotten);
            setPoints( - pointsLost);
            return getPointsProperty();

        }

        @Override
        public VegetableState copy() {
            return new Rotten();
        }

        @Override
        public void grow(Element element) {
            if(element.isGrassProperty().get()){
                if(element.getDuration().get() == 15) { // 25
                    setPoints(- getPoints().get()); //
                    element.setElementState(ElementState.DISAPEAR);
                    //updateScore();
                }
            }
            // si le choux est pourri
            if(element.getDuration().get() == STADE_LOST) { // 25
                setPoints(- getPoints().get()); //
                element.state = new Disapear();
                element.setElementState(ElementState.DISAPEAR);
                //updateScore();
            }

        }

    }
    private class Disapear implements VegetableState{
        @Override
        public void grow(Element element) {}

        @Override
        public void fertilize(Element element) {}

        @Override
        public int harvest(Element element) {
            int pointsLost = (int) Math.ceil(0.1 * MAXIMUM_YIELD * 11);
            setPoints( - pointsLost);
            return getPointsProperty();
        }

        @Override
        public VegetableState copy() {
            return new Disapear();
        }
    }
}

