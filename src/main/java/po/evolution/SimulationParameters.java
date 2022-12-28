package po.evolution;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.opencsv.bean.CsvBindByName;

public class SimulationParameters {
    @CsvBindByName(column = "Map Width", required = true)
    int mapWidth;

    @CsvBindByName(column = "Map Height", required = true)
    int mapHeight;

    @CsvBindByName(column = "World Variant", required = true)
    WorldVariant worldVariant;

    @CsvBindByName(column = "Initial Plant Number", required = true)
    int initialPlantNum;

    @CsvBindByName(column = "Energy From Plant", required = true)
    int energyFromPlant;

    @CsvBindByName(column = "Plants Per Day", required = true)
    int plantsPerDay;

    @CsvBindByName(column = "Plant Growth Variant", required = true)
    PlantGrowthVariant plantGrowthVariant;

    @CsvBindByName(column = "Initial Animal Number", required = true)
    int initialAnimalNum;

    @CsvBindByName(column = "Starting Energy", required = true)
    int startingEnergy;

    @CsvBindByName(column = "Energy Needed", required = true)
    int energyNeeded;

    @CsvBindByName(column = "Procreation Energy Share", required = true)
    double procreationEnergyShare;

    @CsvBindByName(column = "Min Mutations", required = true)
    int minMutations;

    @CsvBindByName(column = "Max Mutations", required = true)
    int maxMutations;

    @CsvBindByName(column = "Mutation Variant", required = true)
    MutationVariant mutationVariant;

    @CsvBindByName(column = "Genotype Length", required = true)
    int genotypeLength;

    @CsvBindByName(column = "Gene Progression Variant", required = true)
    GeneProgressionVariant geneProgressionVariant;

    public String toString() {
        return "Specified Configuration:\n" +
            "Map Width: " + mapWidth + "\n" +
            "Map Height: " + mapHeight + "\n" +
            "World Variant: " + worldVariant.toString() + "\n" +
            "Initial Plant Number: " + initialPlantNum + "\n" +
            "Energy From Plant: " + energyFromPlant + "\n" +
            "Plants Per Day: " + plantsPerDay + "\n" +
            "Plant Growth Variant: " + plantGrowthVariant.toString() + "\n" +
            "Initial Animal Number: " + initialAnimalNum + "\n" +
            "Starting Energy: " + startingEnergy + "\n" +
            "Energy Needed: " + energyNeeded + "\n" +
            "Procreation Energy Share: " + procreationEnergyShare + "\n" +
            "Min Mutations: " + minMutations + "\n" +
            "Max Mutations: " + maxMutations + "\n" +
            "Mutation Variant: " + mutationVariant.toString() + "\n" +
            "Genotype Length: " + genotypeLength + "\n" +
            "Gene Progression Variant: " + geneProgressionVariant.toString() + "\n";
    }

    public Hashtable<String, String> configsInTab() {
        Hashtable<String, String> configTab = new Hashtable<String, String>();
        configTab.put("mapWidth", String.valueOf(mapWidth));
        configTab.put("mapHeight", String.valueOf(mapHeight));
        configTab.put("worldVariant", worldVariant.toString());
        configTab.put("initialPlantNum", String.valueOf(initialPlantNum));
        configTab.put("energyFromPlant", String.valueOf(energyFromPlant));
        configTab.put("plantsPerDay", String.valueOf(plantsPerDay));
        configTab.put("plantGrowthVariant", plantGrowthVariant.toString());
        configTab.put("initialAnimalNum", String.valueOf(initialAnimalNum));
        configTab.put("startingEnergy", String.valueOf(startingEnergy));
        configTab.put("energyNeeded", String.valueOf(energyNeeded));
        configTab.put("procreationEnergyShare", String.valueOf(procreationEnergyShare));
        configTab.put("minMutations", String.valueOf(minMutations));
        configTab.put("maxMutations", String.valueOf(maxMutations));
        configTab.put("mutationVariant", mutationVariant.toString());
        configTab.put("genotypeLength", String.valueOf(genotypeLength));
        configTab.put("geneProgressionVariant", geneProgressionVariant.toString());
        return configTab;
    }

}
