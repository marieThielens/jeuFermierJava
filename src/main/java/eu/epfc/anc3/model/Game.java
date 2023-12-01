package eu.epfc.anc3.model;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import javafx.scene.input.KeyCode;

 class Game {
    private final Land land; // Une instance de la classe Land représentant le monde du jeu
    private final Farmer farmer; // Une instance de la classe Farmer représentant le personnage du joueur
    // Etat du jeu -----
    private final ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(GameStatus.NOT_STARTED); // Une propriété qui indique l'état actuel du jeu
     // le jeu est démarré oui ou non. Calculé à partir de la propriété gameStatus de Game qui représente l'état du jeu
     private final BooleanProperty isStarted = new SimpleBooleanProperty(false);
     private final BooleanProperty isGameSaved = new SimpleBooleanProperty(false);
     private final static IntegerProperty score = new SimpleIntegerProperty(0); // Une propriété qui indique le score actuel du joueur
    private final static IntegerProperty nbDay = new SimpleIntegerProperty(1); // Une propriété qui indique le jour actuel dans le jeu
     // Une instance de la classe MementoCareTaker utilisée pour gérer les sauvegardes de l'état du jeu
    private final  MementoCareTaker careTaker = new MementoCareTaker();

    public Game (){
        Land land = new Land();
        this.land =land;
        // Crée une nouvelle instance de la classe Farmer avec le terrain donné en paramètre
        this.farmer = new Farmer(land);
    }

    void start(){
        gameStatus.set(GameStatus.STARTED);// Change le statut du jeu à "STARTED"
        this.isStarted.set(true);
    }

     void stop() {
        this.land.restartLand(); // Réinitialise le terrain
        this.farmer.restart(); // Réinitialise le personnage du joueur
        this.isGameSaved.set(false); // La sauvegarde disparaît
        score.set(0); // Réinitialise le score
        nbDay.set(1); // Réinitialise le jour
        gameStatus.set(GameStatus.NOT_STARTED); // Change le statut du jeu à "NOT_STARTED"
        this.isStarted.set(false); // modifie l'état du jeu pour indiquer qu'il n'a pas commencé

    }
     // nouveau jour ----------------
     void addNewDay() {
         nbDay.setValue(nbDay.getValue() + 1); // Incrémente le jour actuel
         int lostPoints = land.IncrementVegetableDuration(); // Incrémente la durée des légumes sur le terrain et retourne les points perdus
         score.set(getScoreProperty().get() + lostPoints); // Ajoute les points perdus au score actuel
     }
     void saveGame() {
         createMemento(); // Crée une sauvegarde de l'état du jeu
     }

     // STATUT DU JEU
     BooleanProperty getIsStarted(){ return this.isStarted;}

     ObservableSet<Element> elementsInThePlotProperty(int line, int col){
         // Retourne un ensemble observable des éléments dans la parcelle donnée
        return land.getelementsInThePlotProperty(line,col);
     }
     IntegerProperty getNbJourProperty() {return nbDay;}
     static IntegerProperty getScoreProperty() {
         return score;
     }
     static void setScore(int score) {
         Game.score.set(score);
     }

     // retourne l'état actuel du jeu sous forme d'objet . GameStatus est une enum des différents état du jeu
    private GameStatus status() {
        return this.gameStatus.get();
    }
     BooleanProperty getisGameSavedProperty(){
         return this.isGameSaved;
     }

    //FARMER ---------------------------
     void moveFarmer(KeyCode direction){farmer.moveFarmer(direction);}
     // indique si le fermier se trouve sur une case donnée par les argument ligne et colonne
    ReadOnlyBooleanProperty getHasFarmer(int line, int col){
        return land.hasFarmer(line,col);
    }
    // définit l'action à effectuer par le fermier pass" en paramètre. (planter, etc ? )
    void setFarmerAction(FarmerAction action){
        farmer.setAction(action);
    }
    // Vérifie si le jeu est en cours avant de faire exécuter l'action du fermier
    void farmerAction(){
         if(status() == GameStatus.STARTED){
             farmer.action();
         }
     }
     // Déplace le fermier vers la case souhaitée
    void teleportationFarmer(int line,int col){
        farmer.teleportationFarmer( line, col);
    }

    //MEMENTO -----------------------------

     // Crée un objet memento qui stocke l'état actuel du jeu. Position du fermier
     // nb jour, score, etat grille et stocque l'objet Memento dans careTaker
    private void createMemento() {
        this.isGameSaved.set(true);
        Memento memento = new Memento(this.land.copy(),
                                        this.farmer.copy(),
                                        this.status(),
                                        getScoreProperty().get(),
                                        this.getNbJourProperty().get());
          this.careTaker.saveMemento(memento);
     }
    // restaure l'état du jeu en utilisant l'objet Memento stocké dans careTacker
     void restoreMemento(){
        Memento memento = this.careTaker.restore();
        this.land.restore(memento.land); // met à jour l'état de la grille
        this.farmer.restore(memento.farmer);// position du fermier
        this.gameStatus.set(memento.gameStatus); // état actuel du jeu
        Game.score.set(memento.score);
        Game.nbDay.set(memento.nbDay);

     }

     // Stoque l'état du jeu à un moment donné
     static class Memento {
         private final Land land; // la grille
         private final Farmer farmer; // le fermier
         private final GameStatus gameStatus ; // Enum de l'état du jeu
         private final Integer score ;
         private final Integer nbDay;

         public Memento (Land land, Farmer farmer, GameStatus gameStatus, Integer score, Integer nbDay){
             this.land = land;
             this.farmer = farmer;
             this.gameStatus = gameStatus;
             this.score = score;
             this.nbDay = nbDay;
         }
     }

     // Gérer la sauvegarde et la restauration des objets Memento
      static class MementoCareTaker {
        // un champ "backup" de type Memento qui stocke le dernier état du jeu sauvegardé
         private eu.epfc.anc3.model.Game.Memento backup;
        //  prend un objet Memento en paramètre et le stocke dans le champ "backup"
         void saveMemento(eu.epfc.anc3.model.Game.Memento memento){
             this.backup = memento;
         }
        // retourne l'objet Memento stocké dans le champ "backup"
         eu.epfc.anc3.model.Game.Memento restore(){
             return this.backup;
         }
     }



 }
