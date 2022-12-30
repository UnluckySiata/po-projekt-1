package po.evolution;

import java.security.InvalidParameterException;

public enum SimulationVariant {
    EARTH, INFERNAL_PORTAL, EQUATOR, TOXIC_CORPSES, FULL_RANDOMNESS, SLIGHT_CORRECTION, FULL_PREDESTINATION, SOME_MADNESS;

    public static SimulationVariant parse(String arg) {
        return switch (String.join("", arg).toLowerCase()) {
            case "earth" -> EARTH;
            case "infernal portal" -> INFERNAL_PORTAL;
            case "full randomness" -> FULL_RANDOMNESS;
            case "slight correction" -> SLIGHT_CORRECTION;
            case "equator" -> EQUATOR;
            case "toxic corpses" -> TOXIC_CORPSES;
            case "full predestination" -> FULL_PREDESTINATION;
            case "some madness" -> SOME_MADNESS;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}
