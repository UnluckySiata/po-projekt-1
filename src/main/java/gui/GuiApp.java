package gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class GuiApp extends Application{

    private final GridPane gridPaneScreen = new GridPane();
    private final GridPane gridPaneMap = new GridPane();




    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/forms.fxml"));
        Scene scene = new Scene(root.load(), 600, 853);

        primaryStage.setTitle("Anmial Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        FormController formController = root.getController();
        formController.getStartButton().setOnAction(e -> {
            formController.getUserConfig();
        });
    }

}


