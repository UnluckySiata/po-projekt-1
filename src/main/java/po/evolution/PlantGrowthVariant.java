package po.evolution;

import java.security.InvalidParameterException;

public enum PlantGrowthVariant {
    EQUATOR, TOXIC_CORPSES;

    static PlantGrowthVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
            case "true" -> EQUATOR;
            case "false" -> TOXIC_CORPSES;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}
