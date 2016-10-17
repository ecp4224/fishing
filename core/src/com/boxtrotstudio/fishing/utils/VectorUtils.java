package com.boxtrotstudio.fishing.utils;

/**
 * A utility class for handling vector math
 */
public class VectorUtils {

    /**
     * Checks to see if a point is inside a closed polygon
     * @param point The point to check
     * @param polygon A closed polygon
     * @return True if the point is inside the closed polygon, otherwise false
     */
    public static boolean isPointInside(Vector2f point, Vector2f... polygon) {
        int i, j;
        boolean c = false;
        int nvert = polygon.length;

        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if ( ((polygon[i].y > point.y ) != (polygon[j].y > point.y)) &&
                    (point.x < (polygon[j].x-polygon[i].x) * (point.y-polygon[i].y) / (polygon[j].y-polygon[i].y) + polygon[i].x)   )
                c = !c;
        }

        return c;
    }

    /**
     * Rotate a point
     * @param point The point to rotate
     * @param angle an angle, in radians.
     * @return The new, rotated point
     */
    public static Vector2f rotate(Vector2f point, double angle) {
        float oldx = point.x;

        Vector2f vector2f = new Vector2f();

        vector2f.x = (float) (point.x * Math.cos(angle) - point.y * Math.sin(angle));
        vector2f.y = (float) (point.x * Math.sin(angle) + point.y * Math.cos(angle));

        return vector2f;
    }

    /**
     * Rotate a closed polygon around a center
     * @param angle an angle, in radians.
     * @param center The center to rotate around
     * @param points The closed polygon
     * @return A new array of points that make up the rotated polygon
     */
    public static Vector2f[] rotatePoints(double angle, Vector2f center, Vector2f... points) {
        for (int i = 0; i < points.length; i++) {
            points[i] = center.add(rotate(points[i].sub(center), angle));
        }

        return points;
    }

    public static Vector2f[] createCircle(double radius, int resolution, float x, float y) {
        int iterations = 360 / resolution;
        Vector2f[] points = new Vector2f[iterations];

        for (int i = 0; i < iterations; i++) {
            double val = Math.toRadians(i * (360 / iterations));

            double xx = Math.cos(val) * radius;
            double yy = Math.sin(val) * radius;

            xx += x;
            yy += y;

            points[i] = new Vector2f((float)xx, (float)yy);
        }

        return points;
    }

    public static boolean lineIntersects(Vector2f lineAStart, Vector2f lineAEnd, Vector2f lineBStart, Vector2f lineBEnd) {
        Vector2f temp = new Vector2f(lineBStart.x - lineAStart.x, lineBStart.y - lineAStart.y);
        Vector2f r = new Vector2f(lineAEnd.x - lineAStart.x, lineAEnd.y - lineAStart.y);
        Vector2f s = new Vector2f(lineBEnd.x - lineBStart.x, lineBEnd.y - lineBStart.y);

        float tempxr = temp.x * r.y - temp.y * r.x;
        float tempxs = temp.x * s.y - temp.y * s.x;
        float rxs = r.x * s.y - r.y * s.x;

        if (tempxr == 0f) {

            return ((lineBStart.x - lineAStart.x < 0f) != (lineBStart.x - lineAEnd.x < 0f)) ||
                    ((lineBStart.y - lineAStart.y < 0f) != (lineBStart.y - lineAEnd.y < 0f));
        }

        if (rxs == 0f)
            return false;

        float rxsr = 1f / rxs;
        float t = tempxs * rxsr;
        float u = tempxr * rxsr;

        return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
    }

    public static Vector2f pointOfIntersection(Vector2f lineAStart, Vector2f lineAEnd, Vector2f lineBStart, Vector2f lineBEnd) {
        Vector2f temp = new Vector2f(lineBStart.x - lineAStart.x, lineBStart.y - lineAStart.y);
        Vector2f r = new Vector2f(lineAEnd.x - lineAStart.x, lineAEnd.y - lineAStart.y);
        Vector2f s = new Vector2f(lineBEnd.x - lineBStart.x, lineBEnd.y - lineBStart.y);

        float tempxr = temp.x * r.y - temp.y * r.x;
        float tempxs = temp.x * s.y - temp.y * s.x;
        float rxs = r.x * s.y - r.y * s.x;

        if (tempxr == 0f) {

            return lineAStart;
        }

        if (rxs == 0f)
            return null;

        float rxsr = 1f / rxs;
        float t = tempxs * rxsr;
        float u = tempxr * rxsr;

        if (t >= 0f && t <= 1f && u >= 0f && u <= 1f) {
            return new Vector2f(lineAStart.x + t * r.x, lineAStart.y + t * r.y);
        }
        return null;
    }
}
