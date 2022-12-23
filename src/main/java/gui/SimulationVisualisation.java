package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import po.evolution.AbstractWorldMap;
import po.evolution.SimulationEngine;

public class SimulationVisualisation extends Application{
    private AbstractWorldMap map;
    public void init(Stage primaryStage) throws Exception {
        SimulationEngine simulationEngine = new SimulationEngine("essa", this.map);
        start(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
        Scene scene = new Scene(root.load(), 770, 555);

        primaryStage.setTitle("Symulacja i statystyki");
        primaryStage.setScene(scene);
        SimulationController simulationController = root.getController();
        simulationController.setMap(map);

        simulationController.drawGrid();
        primaryStage.show();
    }
}
