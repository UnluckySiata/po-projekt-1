package po.evolution;

import java.security.InvalidParameterException;
import com.opencsv.bean.CsvBindByName;

enum WorldVariant {
    EARTH, INFERNAL_PORTAL;

    static WorldVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
            case "earth", "ziemia", "kulaziemska" -> EARTH;
            case "infernalportal", "piekielnyportal" -> INFERNAL_PORTAL;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}

enum MutationVariant {
    FULL_RANDOMNESS, SLIGHT_CORRECTION;

    static MutationVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
        case "fullrandomness", "pełnalosowość" -> FULL_RANDOMNESS;
            case "slightcorrection", "lekkakorekta" -> SLIGHT_CORRECTION;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}

enum PlantGrowthVariant {
    EQUATOR, TOXIC_CORPSES;

    static PlantGrowthVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
            case "equator", "zalesionerówniki", "równik" -> EQUATOR;
            case "toxiccorpses", "toksycznetrupy" -> TOXIC_CORPSES;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}

enum GeneProgressionVariant {
    FULL_PREDESTINATION, SOME_MADNESS;

    static GeneProgressionVariant parse(String arg) {
        return switch(String.join("", arg).toLowerCase()) {
            case "fullpredestination" -> FULL_PREDESTINATION;
            case "somemadness" -> SOME_MADNESS;
            default -> throw new InvalidParameterException();
        };
    }
}

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

}
