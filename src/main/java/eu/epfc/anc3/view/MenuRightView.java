package eu.epfc.anc3.view;

import eu.epfc.anc3.vm.MenuRightViewModel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuRightView extends VBox {

    private final MenuRightViewModel menuRightViewModel;
    private final Node node;

    private final Button grassButton = new Button();
    private final Button carotsButton = new Button();
    private final Button cabbageButton = new Button();
    private final Button fertilizedButton = new Button();
    private final Button harvestButton = new Button();

    // Image des boutons
    private static final Image grassImage = new Image("grass.png");
    private static  final Image carrotImage = new Image("carrot3.png");
    private static final Image cabbageImage = new Image("cabbage3.png");
    private static final Image shovelImage = new Image("shovel.png");
    private static final Image wateringImage = new Image("watering_can.png");

    public MenuRightView(MenuRightViewModel menuRightViewModel,Node node) {
        this.menuRightViewModel = menuRightViewModel;
        this.node = node;
        configMenu();
        configButtons();
    }
    private void configMenu(){
        getChildren().addAll(grassButton, carotsButton, cabbageButton, fertilizedButton, harvestButton);
        VBox.setMargin(grassButton, new Insets(0, 0, 10, 0));
        VBox.setMargin(carotsButton, new Insets(0, 0, 10, 0) );
        VBox.setMargin(cabbageButton, new Insets(0, 0, 10, 0));
        VBox.setMargin(fertilizedButton, new Insets(0, 0, 10, 0));
        VBox.setMargin(harvestButton, new Insets(0, 0, 10, 0));
        configLabels();
        configBidings();
    }
    private void configLabels() {
        grassButton.textProperty().bind(menuRightViewModel.grassLabelProperty());
        grassButton.setGraphic(new ImageView(grassImage));
        carotsButton.textProperty().bind(menuRightViewModel.carotsLabelProperty());
        carotsButton.setGraphic(new ImageView(carrotImage));
        cabbageButton.textProperty().bind(menuRightViewModel.cabbageLabelProperty());
        cabbageButton.setGraphic(new ImageView(cabbageImage));
        fertilizedButton.textProperty().bind(menuRightViewModel.fertilizedLabelPropertry());
        fertilizedButton.setGraphic(new ImageView(wateringImage));
        harvestButton.textProperty().bind(menuRightViewModel.harvestLabelProperty());
        harvestButton.setGraphic(new ImageView(shovelImage));
    }
    private void configBidings(){
        grassButton.disableProperty().bind(menuRightViewModel.isRunningRight.not());
        carotsButton.disableProperty().bind(menuRightViewModel.isRunningRight.not());
        cabbageButton.disableProperty().bind(menuRightViewModel.isRunningRight.not());
        fertilizedButton.disableProperty().bind(menuRightViewModel.isRunningRight.not());
        harvestButton.disableProperty().bind(menuRightViewModel.isRunningRight.not());
    }
    private void configButtons() {
        grassButton.setOnMouseClicked(e -> {
            //Mettre a jour le statut du fermier
            menuRightViewModel.plantGrass();
            node.requestFocus();
        });

        cabbageButton.setOnMouseClicked(e -> {
            //Mettre a jour le statut du fermier
            menuRightViewModel.plantCabbage();
            node.requestFocus();
        });

        carotsButton.setOnMouseClicked(e -> {
            //Mettre a jour le statut du fermier
            menuRightViewModel.plantCarrot();
            node.requestFocus();
        });

        fertilizedButton.setOnMouseClicked(e -> {
            //Mettre a jour le statut du fermier
            menuRightViewModel.fertilize();
            node.requestFocus();
        });

        harvestButton.setOnMouseClicked(e -> {
            //Mettre a jour le statut du fermier
            menuRightViewModel.harvest();
            node.requestFocus();
        });
    }
}
