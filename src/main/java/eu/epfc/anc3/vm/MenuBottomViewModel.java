package eu.epfc.anc3.vm;
import eu.epfc.anc3.model.GameFacade;
import javafx.beans.property.*;

public class MenuBottomViewModel {
    private final GameFacade game;
    // pour pouvoir changer le label de mon bouton start
    public BooleanProperty isRunning = new SimpleBooleanProperty(false);
    private final StringProperty startLabelProperty = new SimpleStringProperty("Démarrer");

    public MenuBottomViewModel(GameFacade game) {
        this.game = game;

    }
   public void start() {game.start();}
    public void stop(){
        game.stop();
    }
    public void addNewDay(){
        game.addNewDay();
    }
    public void saveGame() { game.saveGame();}
    public void restoreGame(){game.restore();}

    // binding
    public ReadOnlyBooleanProperty isGameStartedProperty() {return game.isStartedProperty();}
    public ReadOnlyBooleanProperty getisGameSavedProperty(){return game.getisGameSavedProperty();}

    //LABELS
    // texte des mes boutons -----------------------
    public void toggleStartLabel() { // changer le label de mon bouton start
        if (isRunning.get()) { // si le jeu n'es pas démarré
            startLabelProperty.set("Démarrer");
            isRunning.set(false);
        }
        else {
            startLabelProperty.set("Arrêter");
            isRunning.set(true);

        }
    }

    // binding ----
    public StringProperty startLabelProperty() { return startLabelProperty;}
    public StringProperty saveLabelProperty() { return new SimpleStringProperty("Sauvegarder");}
    public StringProperty restoreLabelProperty() { return new SimpleStringProperty("Restaurer");}
    public ReadOnlyStringProperty sleepLabelProperty() {return new SimpleStringProperty("Dormir");}
}
