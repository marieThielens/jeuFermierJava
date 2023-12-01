package eu.epfc.anc3.vm;

import eu.epfc.anc3.model.GameFacade;

public class LandViewModel {
    private final GameFacade game;

    public LandViewModel(GameFacade game) {
        this.game = game;
    }
    public boolean countainsFarmer(int line, int col){
        return game.hasFarmer(line,col).getValue();
    }
    public PlotViewModel getPlotViewModel(int line, int col) { return new PlotViewModel(line, col, game);}

}
