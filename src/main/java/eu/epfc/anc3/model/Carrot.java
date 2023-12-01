package eu.epfc.anc3.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

class Carrot extends Element implements Cloneable{
     final static int MAXIMUM_YIELD = 100;

     public Carrot(){
        this.typeProperty = new SimpleObjectProperty<>(PlotValue.CARROT);
        this.state = new State1();
        this.positionInPlot = 2;
    }

      void setState(ElementState state) {
          this.stateProperty.set(state); ;
     }

      void setElementState(ElementState state){this.stateProperty.set(state);}
      ElementState getElementState(){return this.stateProperty.get();}

      void grow() {
         setDuration(this.getDuration().get() +1);
         state.grow(this);
     }

      void fertilize() {
         state.fertilize(this);
          /*if (pointsProperty == null) {
              pointsProperty = new SimpleIntegerProperty(0);
          }*/
     }

    @Override
    int harvest() {
         return state.harvest(this);
    }

    ReadOnlyIntegerProperty getDuration() {
         return developmentTimeProperty;
     }
      void setDuration(int developmentTime) {
         this.developmentTimeProperty.set(developmentTime);
     }


     ReadOnlyIntegerProperty getPoints(){
         return this.pointsProperty;
     }
     void setPoints(int points){
         this.pointsProperty.set(points);
     }

    @Override
    public Element copy() {
        Element e = new Carrot();
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
         public void grow(Element element) {
             if (element.getDuration().get() > 3) {
                 element.setState(new Carrot.State2());
                 element.setElementState(ElementState.STATE_2);
             }
         }

         @Override
         public void fertilize(Element element) {
                 element.setDuration(7);
                 element.setState(new Carrot.State3());
                 element.setElementState(ElementState.STATE_3);

         }

         @Override
         public int harvest(Element element) {
             setPoints(MAXIMUM_YIELD/10);
             return getPointsProperty();
         }

        @Override
        public VegetableState copy() {
            return new State1();
        }
    }
     private class State2 implements VegetableState {
         @Override
         public void grow(Element element) {
             if (element.getDuration().get() > 6) {
                 element.setState(new Carrot.State3());
                 element.setElementState(ElementState.STATE_3);
             }
         }

         @Override
         public void fertilize(Element element) {
             element.setDuration(7);
             element.setState(new Carrot.State3());
             element.setElementState(ElementState.STATE_3);
         }

         @Override
         public int harvest(Element element) {
             setPoints(MAXIMUM_YIELD/5);
             return getPointsProperty();
         }

         @Override
         public VegetableState copy() {
             return new State2();
         }
     }

     private class State3 implements VegetableState{
         @Override
         public void grow(Element element) {
             if (element.getDuration().get() > 9) {
                 element.setState(new Carrot.State4());
                 element.setElementState(ElementState.STATE_4);
             }
         }

         @Override
         public void fertilize(Element element) {}

         @Override
         public int harvest(Element element) {
             setPoints(MAXIMUM_YIELD/2);
             return getPointsProperty();
         }

         @Override
         public VegetableState copy() {
             return new State3();
         }
     }

     private class State4 implements VegetableState {
         @Override
         public void grow(Element element) {
             if (element.getDuration().get() > 12) {
                 element.setState(new Carrot.Rotten());
                 element.setElementState(ElementState.POURRI);
             }
         }

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
     }

     // pourrissement
     private class Rotten implements VegetableState {
         @Override
         public void grow(Element element) {
             if (element.getDuration().get() == 22) {
                 element.setState(new Carrot.Disapear());
                 element.setElementState(ElementState.DISAPEAR);


             }
         }

         @Override
         public void fertilize(Element element) {}

         @Override
         public int harvest(Element element) {
             int daysRotten = getDuration().get() - 12;
             int pointsLost = (int) Math.ceil(0.1 * MAXIMUM_YIELD * daysRotten);
             setPoints( - pointsLost);
             return getPointsProperty();
         }

         @Override
         public VegetableState copy() {
             return new Rotten();
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
             return null;
         }
     }

}

