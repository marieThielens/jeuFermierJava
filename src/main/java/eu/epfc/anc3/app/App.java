//ANC3 2223 a08
package eu.epfc.anc3.app;

import eu.epfc.anc3.view.FarmView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application  {

    @Override
    public void start(Stage stage) throws Exception {
        FarmView view = new FarmView(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}
