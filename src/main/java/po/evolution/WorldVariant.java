package po.evolution;

import java.security.InvalidParameterException;

public enum WorldVariant {
    EARTH, INFERNAL_PORTAL;

    static WorldVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
            case "earth", "ziemia", "kulaziemska" -> EARTH;
            case "infernalportal", "piekielnyportal" -> INFERNAL_PORTAL;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}
