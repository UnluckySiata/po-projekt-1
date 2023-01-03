package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import po.evolution.*;

import java.util.List;

public class SimulationVisualisation extends Application{

    private SimulationController simulationController;
    private List<SimulationParameters> configs = ConfigurationParser.parse("config.csv");
    private WorldVariant earth = WorldVariant.EARTH;
    private AbstractWorldMap map;

    private SimulationEngine simulationEngine;

    private boolean isPaused = false;
    public void init(Stage primaryStage, SimulationParameters userConfig, boolean toExport, String exportFileName) throws Exception {
        map = earth == WorldVariant.EARTH ? new Earth(userConfig) : new InfernalPortal(userConfig);

        FXMLLoader root = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
        Scene scene = new Scene(root.load(), 1000, 700);

        primaryStage.setTitle("Symulacja i statystyki");
        primaryStage.setScene(scene);
        SimulationController simulationController = root.getController();
        this.simulationController = simulationController;
        if (toExport) {
            simulationEngine = new SimulationEngine(this.map, this.simulationController, exportFileName);
        } else {
            simulationEngine = new SimulationEngine(this.map, this.simulationController);
        }
        simulationController.setMap(map, Integer.parseInt(userConfig.configsInTab().get("startingEnergy")));
        start(primaryStage);
        simulationController.getStopBtn().setOnAction(e -> {
            if (!isPaused) {
                simulationEngine.pauseExecutution();
                isPaused = true;
            } else {
                simulationEngine.resumeExecution();
                isPaused = false;
            }
        });
        simulationController.getEndSimulationBtn().setOnAction(e -> {
            simulationEngine.resumeExecution();
            simulationEngine.terminate();
        });
        simulationController.drawGrid();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.show();
    }
}
