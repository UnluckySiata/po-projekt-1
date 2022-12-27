package po.evolution;

import java.util.Random;

public class InfernalPortal extends AbstractWorldMap {

    public InfernalPortal(SimulationParameters params) {
        super(params);
    }

    public Vector2d nextPosition(Vector2d wantedPosition) {
        int x = wantedPosition.x, y = wantedPosition.y;
        if (x == width || x == -1 || y == height || y == -1) {
            Random r = new Random();
            int newX = x;
            int newY = y;

            while (newX == x || newY == y) {
                x = r.nextInt(width);
                y = r.nextInt(height);
            }

            return new Vector2d(newX, newY);
        }

        return wantedPosition;
    }

}
