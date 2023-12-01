package eu.epfc.anc3.view;

import eu.epfc.anc3.model.*;
import eu.epfc.anc3.vm.PlotViewModel;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


// parcelle
public class PlotView extends StackPane {
    // chercher les images et les afficher
    private static  final Map<String,Image> elementsforPlot = new TreeMap<>();

    // pour remplir la map
    static {
        for (int i = 1; i <= 4; i++) {
            Image cabbageImage = new Image("cabbage" + i + ".png");
            Image carrotImage = new Image("carrot" + i + ".png");
            //Image image = loadImage(imageName);
            elementsforPlot.put("CABBAGE" + "STATE_"+i ,cabbageImage);
            elementsforPlot.put("CARROT" + "STATE_"+i ,carrotImage);
        }
        elementsforPlot.put("dirt",new Image("dirt.png"));
        elementsforPlot.put("farmer",new Image ("farmer.png"));
        elementsforPlot.put("GRASSSTATE_1",new Image ("grass.png"));
        elementsforPlot.put("CARROTPOURRI",new Image("rotten_carrot.png"));
        elementsforPlot.put("CABBAGEPOURRI", new Image("rotten_cabbage.png"));
    }

    //Contenurs des images dans la PlotView
    private final ImageView soilImageView = new ImageView(elementsforPlot.get("dirt"));

    private final ImageView grassImageView = new ImageView();
    private final ImageView vegetableView = new ImageView();
    private final ImageView farmerImageView = new ImageView();
    private PlotViewModel plotViewModel;


    public PlotView(PlotViewModel plotViewModel, DoubleBinding plotWidthProperty, boolean containsFarmer) {
        setImageView(plotWidthProperty);
        //Display the image of farmer
        ReadOnlyBooleanProperty hasFarmer = plotViewModel.hasFarmer();
        //Collection of elements in the plot
        ObservableSet<Element> elementsInThePlot = plotViewModel.elementsInThePlotProperty();

        this.plotViewModel = plotViewModel;

        elementsInThePlot.addListener((SetChangeListener<? super Element>) change -> {
            if(change.wasAdded()){
                // On récupère l'Element ajouter dans le plot
                Element e = change.getElementAdded();

                //En fonction du type d'Element et du state de l'Element,
                //on récupère l'image et on la assigne à son ImageView
                //Le toString d'un element c'est TYPESTATE_nombre
                //
                if(e.getTypeProperty() == PlotValue.GRASS){
                    grassImageView.setImage(elementsforPlot.get(e.toString()));
                }
                else{
                    vegetableView.setImage(elementsforPlot.get(e.toString()));
                    e.currentStateProperty().addListener((ChangeListener<? super ElementState>) (observable, oldValue, newValue)->{
                        vegetableView.setImage(elementsforPlot.get(e.toString()));

                    });
                }

                refreshImageView();
            }
            if(change.wasRemoved()){
                Element e = change.getElementRemoved();
                if(e.getTypeProperty() == PlotValue.GRASS){
                    grassImageView.setImage(null);
                }
                else{
                    vegetableView.setImage(null);
                }
                refreshImageView();
            }


        });

        if(containsFarmer){
            setFarmerImage(true); // permet d'avoir le bonhomme en 0,0
        };

        // déplacer le fermier
        hasFarmer.addListener((obs, old, newVal) -> {
            setFarmerImage(newVal);
        });


        setOnMouseClicked(mouseEvent -> {
            plotViewModel.teleportationFarmer();
        });

        soilImageView.setImage(elementsforPlot.get("dirt"));
    }

    private void setImageView(DoubleBinding plotWidthProperty){
        soilImageView.setPreserveRatio(true);
        soilImageView.fitWidthProperty().bind(plotWidthProperty);
        farmerImageView.setPreserveRatio(true);
        farmerImageView.fitWidthProperty().bind(plotWidthProperty);
        grassImageView.setPreserveRatio(true);
        grassImageView.fitWidthProperty().bind(plotWidthProperty);

        // Ajoute les ImageView à un Pane pour superposer les images
        Pane pane = new Pane();
        pane.getChildren().addAll(soilImageView,grassImageView,vegetableView,farmerImageView);
        getChildren().add(pane);
    }
    private void refreshImageView(){
        // Ajoute les ImageView à un Pane pour superposer les images
        Pane pane = new Pane();
        pane.getChildren().addAll(soilImageView,grassImageView,vegetableView,farmerImageView);
        getChildren().add(pane);
    }


    private void setFarmerImage(boolean plotValue) {
        Image image = null;
        if(plotValue){
             image = elementsforPlot.get("farmer");
        }
        farmerImageView.setImage(image);
    }
}