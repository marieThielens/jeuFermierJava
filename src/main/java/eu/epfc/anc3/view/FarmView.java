package eu.epfc.anc3.view;

import eu.epfc.anc3.model.GameFacade;
import eu.epfc.anc3.vm.FarmViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FarmView extends BorderPane {

    // ViewModel
    private final FarmViewModel farmViewModel = new FarmViewModel();

    // constatnte de mise en page
    static final int PADDING = 20;
    static final int MENU_WIDTH = 200;
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;
    static final int LAND_WIDTH = GameFacade.landWidth();
    static final int LAND_HEIGHT = GameFacade.landHeight();

    // contrainte de mise en page (taille de ma grille
    private final DoubleProperty landWidthProperty = new SimpleDoubleProperty(600);
    private final DoubleProperty landHeightProperty = new SimpleDoubleProperty(500);

    // Composant principaux
    private LandView landView;
    private MenuBottomView menuBottomView;
    private MenuTopView menuTopView;
    private MenuRightView menuRightView;

    public FarmView(Stage primaryStage) { // stage = fenetre principale
        start(primaryStage);
    }

    public void start(Stage stage) {
        // mise en place des composants principaux
        configMainComponents(stage);

        // mise en place de la scène et affichage de la fenetre
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        stage.setScene(scene); //Scene : conteneur principal

        // Adapter la taille de la scene quand on redimensionne
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue() - MENU_WIDTH - (2 * PADDING);
            landWidthProperty.set(newWidth);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue() - menuTopView.getHeight() - menuBottomView.getHeight() - (2 * PADDING);
            landHeightProperty.set(newHeight);
        });


        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

    }
    private void configMainComponents(Stage stage) {
        // le titre de mon app défini dans vm
        stage.titleProperty().bind(farmViewModel.titleProperty());

        // mise en place des composants du menu
        configMenu();
        // mise en place du terrain
        configLandView();
        //Déplacement du joueur
        configMove();
        //Action du joueur
        actionFarmer();
    }

    // Pour le menu ---------
    private void configMenu() {

        // Récupérer le modele de mes boutons du bas
        menuBottomView = new MenuBottomView(farmViewModel.getMenuBottomViewModel(),this, farmViewModel.getMenuRightViewModel());
        setBottom(menuBottomView);
        menuTopView = new MenuTopView(farmViewModel.getMenuTopViewModel());
        setTop(menuTopView);
        menuRightView = new MenuRightView(farmViewModel.getMenuRightViewModel(), this);

        // avoir un VBox pour aligner au centre verticalement
        VBox vBox = new VBox(menuRightView);
        vBox.setAlignment(Pos.CENTER);
        setRight(vBox);
        BorderPane.setMargin(vBox, new Insets(0, 10, 0, 0));
    }

    private void configLandView() {
        createLandPane();
        farmViewModel.isGameStartedProperty().addListener(
            (obs, oldval, newval) -> {
                configLandPane(newval);
            });
    }
    private void configLandPane(boolean gameStarted) {
        if(gameStarted) {
            createLandPane(); // rajoute grille
        }
    }

    private void createLandPane(){
        createLand();
    }
    // Construction graphique de la vue du terrain
    private void createLand() {
        landView = new LandView(farmViewModel.getLandViewModel(), landWidthProperty);
        // La forme du terrain
        landView.minWidthProperty().bind(landWidthProperty); // je lie à mes contraintes en haut
        landView.minHeightProperty().bind(landHeightProperty);
        landView.maxHeightProperty().bind(landHeightProperty);
        landView.maxWidthProperty().bind(landWidthProperty);

        setCenter(landView); // centrer le terrain
    }
    private void configMove(){
        this.setOnKeyPressed(k -> {
                    farmViewModel.moveFarmerr(k.getCode());
                }
        );
    }
    private void actionFarmer(){
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.SPACE) {
                farmViewModel.farmerAction();
            }
        });
    }

}
