package po.evolution;

import gui.FormController;
import gui.GuiApp;
import javafx.application.Application;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<SimulationParameters> configs = ConfigurationParser.parse("config.csv");

        AbstractWorldMap map = new Earth(configs.get(0));
        //SimulationEngine engine = new SimulationEngine(map);
        int animals = map.animalsNum, plants = 0;
        System.out.println(animals == map.animals.size());
        for (boolean b: map.plantPresent) {
            if (b) ++plants;
        }

        System.out.println("animals: " + animals + " plants: " + plants);
        Application.launch(GuiApp.class, args);
    }
}
