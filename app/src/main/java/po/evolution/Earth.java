package po.evolution;

public class Earth extends AbstractWorldMap {

    public Earth(int width, int height, SimulationParameters params) {
        super(height, width, params);
    }

    public Vector2d nextPosition(Vector2d wantedPosition) {
        int x = 0, y = 0;
        if (wantedPosition.x == -1) x = width;
        else if (wantedPosition.x == width) x = 0;
        else x = wantedPosition.x;

        if (wantedPosition.y == height) y = height - 1;
        else if (wantedPosition.y == -1) y = 0;
        else y = wantedPosition.y;

        return new Vector2d(x, y);

    }
}
