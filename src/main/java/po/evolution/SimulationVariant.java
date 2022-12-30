package po.evolution;

import java.security.InvalidParameterException;

public enum SimulationVariant {
    EARTH, INFERNAL_PORTAL, EQUATOR, TOXIC_CORPSES, FULL_RANDOMNESS, SLIGHT_CORRECTION, FULL_PREDESTINATION, SOME_MADNESS;

    public static SimulationVariant parse(String arg) {
        return switch (String.join("", arg).toLowerCase()) {
            case "kula ziemska" -> EARTH;
            case "piekielny portal" -> INFERNAL_PORTAL;
            case "pełna losowość" -> FULL_RANDOMNESS;
            case "lekka korekta" -> SLIGHT_CORRECTION;
            case "zalesione równiki" -> EQUATOR;
            case "toksyczne trupy" -> TOXIC_CORPSES;
            case "pełna predystancja" -> FULL_PREDESTINATION;
            case "nieco szaleństwa" -> SOME_MADNESS;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}
