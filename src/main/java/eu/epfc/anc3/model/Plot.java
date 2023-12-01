package eu.epfc.anc3.model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import java.util.TreeSet;


public class Plot {

    // retient quel element dans quelle plot
    private ObservableSet<Element> elementsInThePlot = FXCollections.observableSet(new TreeSet<>()); ;
    private final BooleanProperty hasFarmer = new SimpleBooleanProperty(false);

    public Plot(){
        initialize();
    }

    // initialise en y ajoutant un sol par défaut
    void initialize(){
        elementsInThePlot.add(new Soil());
    }
    ObservableSet<Element> getElementsInThePlot(){
        return this.elementsInThePlot;
    }
    // Plante un élément de la valeur spécifiée dans la parcelle selon le type d'élément
    void plantElement (PlotValue value){
        switch (value){
            case GRASS -> {
                //Vérifie qu'il n'existe aucune herbe avant de l'ajouter
                if(!isContainGrass()) {
                    elementsInThePlot.add(new Grass());
                    // parcours tous les elements pour vérifier si il y a du gazon. Utilse pour le calcul de point
                    elementsInThePlot.forEach(e ->e.setIsGrassProperty(true));
                }
            }

            case CARROT ->{
                if(!isContainsVegetable()) {
                    elementsInThePlot.add(new Carrot());
                }
            }
            case CABBAGE -> {
                if(!isContainsVegetable()) {
                    elementsInThePlot.add(new Cabbage());
                    //DOIT ASSOCIER LE GAZON AU CHOUX POUR LE CALCUL DES POINTS
                    /* vérifie si la parcelle contient de l'herbe, puis sélectionne tous les choux de la
                    parcelle et les transforme en herbe en appelant la méthode */
                    if(isContainGrass()){
                        elementsInThePlot.stream()
                                // seul les elements de la parcelle qui ont des choux seront traité ultérieurement
                                .filter(e -> e.typeProperty.get() == PlotValue.CABBAGE)
                                // pour chaque element qui a passé le filtre, permet de le transformer en herbe
                                .forEach(e ->e.setIsGrassProperty(true));
                    }
                }
            }
        }
    }
    // Fait pousser tous les éléments présents dans la parcelle et retourne le nombre de points perdus.
    int growElements(){
        int lostPoints = 0;
        elementsInThePlot.forEach(Element::grow);
        // Vérifie si la parcelle contient un élément qui doit disparaitre
        if(isContainsDisapearElement ()){
            // récolte l'élément qui doit disparaître et renvoie le nombre de points perdus en le récoltan
            lostPoints = harvestDisapearElement();
        }
        return lostPoints;

    }
    // Récolte tous les éléments présents dans la parcelle et retourne le nombre de points gagnés.
    int harvestElement(){
        int totalPoints = 0;
        // Vérifie si ne contient pas de légumes.
            if(!isContainsVegetable()){
                elementsInThePlot.forEach(e ->e.setIsGrassProperty(false));
                // Pour retirer l'herbe
                elementsInThePlot.removeIf(element -> element.getTypeProperty() == PlotValue.GRASS);
            }
            else{
                // itérer à travers tous les élémants de la parcelle
                totalPoints = elementsInThePlot.stream()
                                                // filtre par type de l'éléments
                                                .filter(element -> element.getTypeProperty() == PlotValue.CABBAGE ||
                                                element.getTypeProperty() == PlotValue.CARROT)
                                                .mapToInt(Element::harvest) // map chaque élément en un int
                                                .sum(); // rajouter la somme de chaque el mappé dans le total des points
                //Retire l'élément en fonction de son type
                //Changer le statut de la propriété du choux is_Grass
                elementsInThePlot.removeIf(element -> element.getTypeProperty() == PlotValue.CARROT);
                elementsInThePlot.removeIf(element -> element.getTypeProperty() == PlotValue.CABBAGE);
            }

        return totalPoints;
    }

    // Récolte les éléments qui ont un état de disparition (disapear) et retourne le nombre de points perdus.
    int harvestDisapearElement(){
        int totalLostPoint = 0;
        if(isContainsDisapearElement ()){
            if(elementsInThePlot.stream()
                                .anyMatch(element -> element.currentStateProperty().get() == ElementState.DISAPEAR &&
                                element.getTypeProperty() == PlotValue.GRASS)){
                elementsInThePlot.forEach(e ->e.setIsGrassProperty(false));
                // elementsInThePlot.removeIf(element -> element.getTypeProperty() == PlotValue.GRASS);
            }
            totalLostPoint = elementsInThePlot.stream()
                    .filter(element -> element.currentStateProperty().get() == ElementState.DISAPEAR)
                    .mapToInt(Element::harvest) // map chaque élément en int
                    .sum(); // adiitionne chaque int de la map

            elementsInThePlot.removeIf(element -> element.currentStateProperty().get() == ElementState.DISAPEAR);
        }
        return totalLostPoint;
    }
    // Fertilise tous les éléments présents dans la parcelle.
    void fertilize(){
        elementsInThePlot.forEach(Element::fertilize);
    }
    //  Retourne true si la parcelle contient des légumes (carotte ou chou), false sinon.
    private boolean isContainsVegetable(){
        return elementsInThePlot.stream().anyMatch(e ->e.getElementType() == PlotValue.CARROT ||
                                                        e.getElementType() == PlotValue.CABBAGE);

    }

    private boolean isContainGrass(){
        return  elementsInThePlot.stream().anyMatch(e -> e.typeProperty.get() == PlotValue.GRASS);
    }

    // Retourne true si la parcelle contient des éléments ayant un état de disparition, false sinon.
    private boolean isContainsDisapearElement (){
        return elementsInThePlot.stream().anyMatch(element -> element.currentStateProperty().get() == ElementState.DISAPEAR);
    }
    // Retourne la propriété booléenne indiquant si la parcelle a un fermier
    BooleanProperty getHasFarmer (){
        return hasFarmer;
    }
    // Modifie la propriété booléenne indiquant si la parcelle a un fermier
    void setFamer(){
        hasFarmer.setValue(!hasFarmer.getValue());
    }

    // Crée une copie de la parcelle, avec une copie de chaque élément présent
    Plot copy() {
        Plot p = new Plot(); // crée un nouveau plot en utilisant le constructeur par défaut
        // crée une nouvelle collection triée de plot
        p.elementsInThePlot =  FXCollections.observableSet(new TreeSet<>());
        // parcourir la collection de plot et faire une copie
        for(Element e : this.elementsInThePlot){
            p.elementsInThePlot.add(e.copy());
        }
        return p;

    }

    // Restaure la parcelle avec les éléments de la parcelle spécifiée.
    void restore(Plot plot){
        // supprimer tous les éléments du terrain courant
        this.elementsInThePlot.clear();
        for(Element e : plot.elementsInThePlot){
            // cela ajoute chaque élément de l'objet plot au terrain courant en créant une copie de chaque élément.
            // Ainsi, le terrain courant aura des éléments identiques mais différents en mémoire de ceux de l'objet plot.
            this.elementsInThePlot.add(e.copy());
        }
        // Pour garantir que l'ordre des elements est préservés
        this.elementsInThePlot.addAll(plot.elementsInThePlot);
        // pour le fermier
        this.hasFarmer.set(plot.hasFarmer.getValue());
    }

    @Override
    public String toString() {
        return "Plot{" +
                "elementsInThePlot=" + elementsInThePlot +
                ", hasFarmer=" + hasFarmer +
                '}';
    }
}
