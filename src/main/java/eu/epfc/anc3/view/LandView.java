package eu.epfc.anc3.view;

import eu.epfc.anc3.vm.LandViewModel;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static eu.epfc.anc3.view.FarmView.PADDING;
import static eu.epfc.anc3.view.FarmView.LAND_WIDTH;
import static eu.epfc.anc3.view.FarmView.LAND_HEIGHT;

public class LandView extends GridPane {

    public LandView(LandViewModel landViewModel, DoubleProperty landWidthProperty){

        // Cellules de même taille
        RowConstraints rowConstraints = new RowConstraints();
        //rowConstraints.setPercentHeight(100.0 / LAND_HEIGHT);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        //columnConstraints.setPercentWidth(100.0 / LAND_WIDTH);
        DoubleBinding plotWidthProperty = landWidthProperty.divide(LAND_WIDTH);
        // setMargin(this, new Insets(100, 0, 0, 0));
        // largeur, contraine de taille
        for (int i = 0; i < LAND_WIDTH; ++i) {
            getColumnConstraints().add(columnConstraints);
           //  getRowConstraints().add(rowConstraints);
        }
        // hauteur
        for(int i = 0; i < LAND_HEIGHT; ++i) {
            getRowConstraints().add(rowConstraints);
           // getColumnConstraints().add(columnConstraints);
        }

        // Remplissage de la grille
        for (int i = 0; i < LAND_WIDTH; ++i) {
            for (int j = 0; j < LAND_HEIGHT; ++j) {
                boolean containsFarmer = landViewModel.countainsFarmer(i,j);
                PlotView plotView = new PlotView(landViewModel.getPlotViewModel(i, j), plotWidthProperty, containsFarmer);
                add(plotView, i, j); // lignes/colonnes inversées dans gridpane
            }
        }
    }
}
