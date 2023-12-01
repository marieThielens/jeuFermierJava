package eu.epfc.anc3.model;

public interface VegetableState {
    void grow(Element element);
    void fertilize(Element element);
    int harvest(Element element);
    VegetableState copy();
}
