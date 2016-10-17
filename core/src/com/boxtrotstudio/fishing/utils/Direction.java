package com.boxtrotstudio.fishing.utils;

public enum Direction {
    /**
     * Going away from the y origin
     */
    UP(0, 1),

    /**
     * Moving towards the y origin
     */
    DOWN(0, -1),

    /**
     * Moving towards the x origin
     */
    LEFT(-1, 0),

    /**
     * Moving away from the x origin
     */
    RIGHT(1, 0),

    /**
     * A complex direction <br></br>
     * Moving away from the y origin and towards the x origin
     */
    UP_LEFT(-1, 1),

    /**
     * A complex direction <br></br>
     * Moving away from the y origin and away from the x origin
     */
    UP_RIGHT(1, 1),

    /**
     * A complex direction <br></br>
     * Moving towards the y origin and towards the x origin
     */
    DOWN_LEFT(-1, -1),

    /**
     * A complex direction <br></br>
     * Moving towards from the y origin and away from the x origin
     */
    DOWN_RIGHT(1, -1),

    /**
     * Represents no direction, or not moving.
     */
    NONE(0, 0);

    int x, y;
    Direction(int x, int y) { this.x = x; this.y = y; }

    /**
     * Converts a complex direction into a simple direction.
     * @return The only 2 results this can return is either {@link Direction#UP} or {@link Direction#DOWN} if this direction is a complex direction, otherwise it will return itself
     */
    public Direction simple() {
        switch (this) {
            case UP_RIGHT:
            case UP_LEFT:
                return UP;
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return DOWN;
            default:
                return this;
        }
    }

    public Direction add(Direction dir) {
        int x = dir.x + this.x;
        int y = dir.y + this.y;

        return fromComponent(x, y);
    }

    public Direction sub(Direction dir) {
        int x = this.x - dir.x;
        int y = this.y - dir.y;

        return fromComponent(x, y);
    }

    /**
     * Get the opposite direction of this direction. For example, if this direction is {@link Direction#RIGHT}, then this method will return {@link Direction#LEFT}. <br></br>
     * If a complex direction is given, then a complex direction is returned. The complex direction returned will always be the opposite of what was given.
     * @return The opposite direction of this direction.
     */
    public Direction opposite() {
        int x = 0, y = 0;

        if (this.x == -1)
            x = 1;
        else if (this.x == 1)
            x = -1;

        if (this.y == -1)
            y = 1;
        else if (this.y == 1)
            y = -1;

        return Direction.fromComponent(x, y);
    }

    /**
     * Rotate this direction 90 degrees clockwise
     * @return The resulting direction
     */
    public Direction rotateNegitive90() {
        return opposite().rotate90();
    }

    /**
     * Rotate this direction 90 degrees counter-clockwise
     * @return The resulting direction
     */
    public Direction rotate90() {
        Vector2f temp = new Vector2f(x, y);
        temp.rotate(Math.toRadians(90));

        return fromComponent((int)temp.x, (int)temp.y);
    }

    public Vector2f toVector() {
        return new Vector2f(x, y);
    }

    public static Direction fromDegrees(double value) {
        double degrees = validateDegree(value);

        if (degrees <= 30) {
            return RIGHT;
        } else if (degrees > 30 && degrees <= 60) {
            return UP_RIGHT;
        } else if (degrees > 60 && degrees <= 120) {
            return UP;
        } else if (degrees > 120 && degrees <= 150) {
            return UP_LEFT;
        } else if (degrees > 150 && degrees <= 210) {
            return LEFT;
        } else if (degrees > 210 && degrees <= 240) {
            return DOWN_LEFT;
        } else if (degrees > 240 && degrees <= 300) {
            return DOWN;
        } else if (degrees > 300 && degrees <= 330) {
            return DOWN_RIGHT;
        } else {
            return RIGHT;
        }
    }

    public static Direction fromVector(Vector2f vector) {
        if (vector.length() == 0)
            return NONE;

        double inv = Math.atan2(vector.y, vector.x);

        return fromDegrees(Math.toDegrees(inv));
    }

    private static double validateDegree(double degree) {
        while (degree > 360) {
            degree -= 360;
        }

        while (degree < 0) {
            degree += 360;
        }

        return degree;
    }

    private static Direction fromComponent(int x, int y) {
        x = Math.max(-1, Math.min(1, x));
        y = Math.max(-1, Math.min(1, y));

        for (Direction d : values()) {
            if (d.x == x && d.y == y)
                return d;
        }

        return NONE;
    }

    public Direction getXDirection() {
        return fromComponent(x, 0);
    }

    public Direction getYDirection() {
        return fromComponent(0, y);
    }
}