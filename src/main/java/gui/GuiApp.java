package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import po.evolution.ConfigurationParser;
import po.evolution.SimulationParameters;

import java.util.List;
import java.util.Objects;

public class GuiApp extends Application{
    private List<SimulationParameters> configs = ConfigurationParser.parse("config.csv");
    private boolean toExport = false;

    private String exportFileName = "";
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/forms.fxml"));
        Scene scene = new Scene(root.load(), 620, 853);

        primaryStage.setTitle("Anmial Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        FormController formController = root.getController();
        formController.getStartButton().setOnAction(e -> {
            Stage stage = new Stage();
            try {
                new SimulationVisualisation().init(stage, formController.getUserConfig(), toExport, exportFileName);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        formController.getExConfig1Btn().setOnAction(e -> {
            formController.displayExampleConfig(configs.get(0));
        });
        formController.getExConfig2Btn().setOnAction(e -> {
            formController.displayExampleConfig(configs.get(1));
        });
        formController.getExportToCsvBtn().setOnAction(e -> {
            exportFileName = formController.getExportFileName();
            toExport = !exportFileName.equals("");
        });
    }

}


