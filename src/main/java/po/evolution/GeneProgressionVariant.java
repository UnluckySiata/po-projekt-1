package po.evolution;

import java.security.InvalidParameterException;

public enum GeneProgressionVariant {
    FULL_PREDESTINATION, SOME_MADNESS;

    static GeneProgressionVariant parse(String arg) {
        return switch(String.join("", arg).toLowerCase()) {
            case "true" -> FULL_PREDESTINATION;
            case "false" -> SOME_MADNESS;
            default -> throw new InvalidParameterException();
        };
    }
}
