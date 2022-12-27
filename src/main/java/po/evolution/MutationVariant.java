package po.evolution;

import java.security.InvalidParameterException;

public enum MutationVariant {
    FULL_RANDOMNESS, SLIGHT_CORRECTION;

    static MutationVariant parse(String arg) throws InvalidParameterException {
        return switch(String.join("", arg).toLowerCase()) {
            case "true" -> FULL_RANDOMNESS;
            case "false" -> SLIGHT_CORRECTION;
            default -> throw new InvalidParameterException("Can't match parmeter to enum variant");
        };
    }
}
