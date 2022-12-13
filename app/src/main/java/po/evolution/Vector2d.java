package po.evolution;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        if (this.x <= other.x && this.y <= other.y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean follows(Vector2d other) {
        if (this.x >= other.x && this.y >= other.y) {
            return true;
        } else {
            return false;
        }
    }

    public Vector2d upperRight(Vector2d other) {
        int x, y;
        if (this.x > other.x) x = this.x;
        else x = other.x;

        if (this.y > other.y) y = this.y;
        else y = other.y;

        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x, y;
        if (this.x < other.x) x = this.x;
        else x = other.x;

        if (this.y < other.y) y = this.y;
        else y = other.y;

        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
