package com.boxtrotstudio.fishing.utils;

public class CosSineTable {
    private static double[] cos = new double[361];
    private static double[] sin = new double[361];

    static {
        for (int i = 0; i <= 360; i++) {
            cos[i] = Math.cos(Math.toRadians(i));
            sin[i] = Math.sin(Math.toRadians(i));
        }
    }

    public static double getSine(int angle) {
        angle = validateAngle(angle);

        int angleCircle = angle % 360;
        return sin[angleCircle];
    }

    public static double getCos(int angle) {
        angle = validateAngle(angle);

        int angleCircle = angle % 360;
        return cos[angleCircle];
    }

    private static int validateAngle(int angle) {
        while (angle > 360) {
            angle -= 360;
        }

        while (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
