package po.evolution;

import java.security.InvalidParameterException;

public enum GeneProgressionVariant {
    FULL_PREDESTINATION, SOME_MADNESS;

    static GeneProgressionVariant parse(String arg) {
        return switch(String.join("", arg).toLowerCase()) {
            case "fullpredestination" -> FULL_PREDESTINATION;
            case "somemadness" -> SOME_MADNESS;
            default -> throw new InvalidParameterException();
        };
    }
}
