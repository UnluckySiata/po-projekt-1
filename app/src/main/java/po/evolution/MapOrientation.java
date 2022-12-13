package po.evolution;

public enum MapOrientation {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    private static MapOrientation[] list = MapOrientation.values();

    public static MapOrientation getMapOrientation(int i) {
        return list[i];
    }

    public MapOrientation rotate(int i) {
        int curr;
        for (curr = 0; curr < list.length; ++curr) {
            if (list[curr] == this) break;
        }

        return list[(curr + i) % list.length];
    }


    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
            default -> null;
        };
    }

}
