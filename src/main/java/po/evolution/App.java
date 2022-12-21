package po.evolution;

import gui.formController;
import javafx.application.Application;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<SimulationParameters> configs = ConfigurationParser.parse("config.csv");


//        for (SimulationParameters c: configs) {
//            System.out.println(c.toString());
//        }
        Application.launch(formController.class, args);
    }
}