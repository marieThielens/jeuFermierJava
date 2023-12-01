package eu.epfc.anc3.vm;


import eu.epfc.anc3.model.GameFacade;
import javafx.beans.property.*;
import javafx.scene.input.KeyCode;

public class FarmViewModel {
    private final MenuTopViewModel menuTopViewModel;
    private final MenuBottomViewModel menuBottomViewModel;
    private final MenuRightViewModel menuRightViewModel;
    private final LandViewModel landViewModel;
    private final GameFacade game = new GameFacade();

    public MenuBottomViewModel getMenuBottomViewModel() { return  menuBottomViewModel;}
    public MenuTopViewModel getMenuTopViewModel() { return menuTopViewModel; }
    public MenuRightViewModel getMenuRightViewModel() { return menuRightViewModel;}
    public LandViewModel getLandViewModel(){ return landViewModel;}

    public FarmViewModel() {
        landViewModel = new LandViewModel(game);
        menuTopViewModel = new MenuTopViewModel(game);
        menuBottomViewModel = new MenuBottomViewModel(game);
        menuRightViewModel = new MenuRightViewModel(game, menuBottomViewModel);
    }

    public void moveFarmerr(KeyCode code){
        game.moveFarmer(code);

    }

    public void farmerAction(){
        game.farmerAction();
    }

    // le titre de l'app
    public ReadOnlyStringProperty titleProperty() { return new SimpleStringProperty("Farm game"); }

    public ReadOnlyBooleanProperty isGameStartedProperty() {
        return game.isStartedProperty();
    }
}
