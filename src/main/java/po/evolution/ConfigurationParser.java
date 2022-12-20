package po.evolution;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import com.opencsv.bean.CsvToBeanBuilder;

class ConfigurationParserException extends Exception {
    ConfigurationParserException(String errorMessage) {
        super(errorMessage);
    }

}

public class ConfigurationParser {

    public static List<SimulationParameters> parse(String fileName) {
        try {
            FileReader reader = new FileReader("src/main/resources/" + fileName);
            List<SimulationParameters> beans = new CsvToBeanBuilder<SimulationParameters>(reader)
                .withType(SimulationParameters.class).build().parse();
            return beans;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;

    }
}
