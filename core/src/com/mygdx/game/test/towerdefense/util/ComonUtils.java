package com.mygdx.game.test.towerdefense.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ball.Ball;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComonUtils {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void onCollision(CopyOnWriteArrayList<? extends Sprite> ghosts,
                                   CopyOnWriteArrayList<? extends Sprite> bullets) {
        for (Sprite ball : ghosts) {
            for (Sprite bullet : bullets) {
                if (ComonUtils.distance(bullet.getX(), bullet.getY(), ball.getX(), ball.getY())
                        < (ball.getWidth() + bullet.getWidth())) {
                    ghosts.remove(ball);
                    bullets.remove(bullet);
                }
            }
        }
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

    public static float sin(double angle) {
        return (float) Math.sin(angle / 180 * Math.PI);
    }

    public static float cos(double angle) {
        return (float) Math.cos(angle / 180f * Math.PI);
    }
}
