package eu.epfc.anc3.view;

import eu.epfc.anc3.vm.MenuBottomViewModel;
import eu.epfc.anc3.vm.MenuRightViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MenuBottomView extends HBox {

    private final Button startButton = new Button();
    private final Button sleepButton = new Button();
    private final Button saveButton = new Button();
    private final Button restoreButton = new Button();

    private final MenuBottomViewModel menuBottomViewModel;
    //private final Node node;
    private final FarmView farmView;
    private final MenuRightViewModel menuRightViewModel;

    // Node node
    public MenuBottomView(MenuBottomViewModel menuBottomViewModel, FarmView farmView, MenuRightViewModel menuRightViewModel) {
        this.menuBottomViewModel = menuBottomViewModel;
        this.farmView = farmView;
        this.menuRightViewModel = menuRightViewModel;

        configMenu();

        setAlignment(Pos.BOTTOM_CENTER);
        setPadding(new Insets(50));
    }

    private void configMenu() {
        getChildren().addAll(startButton,sleepButton, saveButton, restoreButton);
        HBox.setMargin(startButton, new Insets(0, 20, 0, 0));
        HBox.setMargin(sleepButton, new Insets(0, 20, 0, 0));
        HBox.setMargin(saveButton, new Insets(0, 20, 0, 0));


        configLabels(); // le texte des boutons
        configBiddings();
        configButtons();

    }
    private void configLabels() { // le texte de mes boutons
        startButton.textProperty().bind(menuBottomViewModel.startLabelProperty());
        sleepButton.textProperty().bind(menuBottomViewModel.sleepLabelProperty());
        saveButton.textProperty().bind(menuBottomViewModel.saveLabelProperty());
        restoreButton.textProperty().bind(menuBottomViewModel.restoreLabelProperty());
    }

    private void configBiddings(){
        sleepButton.disableProperty().bind(menuBottomViewModel.isGameStartedProperty().not());
        saveButton.disableProperty().bind(menuBottomViewModel.isGameStartedProperty().not());
        restoreButton.disableProperty().bind(menuBottomViewModel.isGameStartedProperty().not());
        restoreButton.disableProperty().bind((menuBottomViewModel.getisGameSavedProperty().not()));
    }

    private void configButtons() {
        startButton.setOnAction(e -> {
            menuBottomViewModel.start();
            farmView.requestFocus();
            // Quand je clique sur stop
            if(menuBottomViewModel.isRunning.get()) {
                menuBottomViewModel.stop();
                menuBottomViewModel.toggleStartLabel();

            } else {
                menuBottomViewModel.start();
                menuBottomViewModel.toggleStartLabel();
            }
        });

        sleepButton.setOnAction(e -> {
            menuBottomViewModel.addNewDay();
            farmView.requestFocus();
        });

        saveButton.setOnAction(e -> {
            // si on clique sur save le bouton restaurer devient actif
            menuBottomViewModel.saveGame();
            farmView.requestFocus();
        });

        restoreButton.setOnMouseClicked(e -> {
            menuBottomViewModel.restoreGame();
            farmView.requestFocus();
        });

    }
}
