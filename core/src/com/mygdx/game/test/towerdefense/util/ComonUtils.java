package com.mygdx.game.test.towerdefense.util;

import java.util.Arrays;

public class ComonUtils {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public static double sin(double angle) {
        return Math.sin(angle / 180 * Math.PI);
    }

    public static double cos(double angle) {
        return Math.cos(angle / 180 * Math.PI);
    }
}
