package gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class GuiApp extends Application{

    private final GridPane gridPaneScreen = new GridPane();
    private final GridPane gridPaneMap = new GridPane();

    int mapWidth;
    int mapHeight;
    String worldVariant;
    String plantGrowthVariant;
    String mutationVariant;
    int initialPlantNumber;
    int energyFromPlant;
    int startingEnergy;
    int animalQuantity;
    int energyNeeded;
    int copulationEnergy;
    double procreationEnergyShare;
    int minMutations;
    int maxMutations;
    int genotypeLength;
    String geneProgressionVariant;


    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}


