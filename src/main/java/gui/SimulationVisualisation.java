package gui;

import com.opencsv.bean.CsvBindByName;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import po.evolution.*;

import java.util.List;
import com.opencsv.bean.CsvBindByName;

public class SimulationVisualisation extends Application{

    private List<SimulationParameters> configs = ConfigurationParser.parse("config.csv");
    private WorldVariant earth = WorldVariant.EARTH;
    private AbstractWorldMap map;
    public void init(Stage primaryStage) throws Exception {
        map = earth == WorldVariant.EARTH ? new Earth(10, 10, configs.get(0)) : new InfernalPortal(10, 10, configs.get(0));
        SimulationEngine simulationEngine = new SimulationEngine("essa", this.map);
        start(primaryStage);

        FXMLLoader root = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
        Scene scene = new Scene(root.load(), 770, 555);

        primaryStage.setTitle("Symulacja i statystyki");
        primaryStage.setScene(scene);
        SimulationController simulationController = root.getController();
        simulationController.setMap(map);

        simulationController.drawGrid();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.show();
    }
}
