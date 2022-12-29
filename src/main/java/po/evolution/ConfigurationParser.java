package po.evolution;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.opencsv.CSVWriter;
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

    public static SimulationParameters writeAndGet(List<String> userConfig, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/" + fileName, true);
            CSVWriter writer = new CSVWriter(fileWriter);
            writer.writeNext(userConfig.toArray(new String[0]));
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<SimulationParameters> userConfigs = parse(fileName);
        return userConfigs.get(userConfigs.size() - 1);

    }
}
