package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class RoadMapTransformer {

    public static List<UnitTower> transform(int width, int height) {
        List<UnitTower> balls = new ArrayList<>();
        int widthSplit = width / RoadMap.Y;
        int heightSplit = height / RoadMap.X;

        for (int i = 0; i < RoadMap.X; i++) {
            for (int j = 0; j < RoadMap.Y; j++) {
                UnitTower ball = new UnitTower(
                        heightSplit * j,
                        widthSplit * (RoadMap.X - i),
                        10,
                        0,
                        0,
                        RoadMap.MAP[i][j] == RoadMap.RODE
                                ? Color.GREEN
                                : Color.RED
                );
                ball.setMapOriginPosition(new Node(i, j));
                ball.setGameOriginPosition(new Node(j, RoadMap.X - i));
                ball.setGameFinalPosition(new Node(heightSplit * j, widthSplit * (RoadMap.X - i)));
                balls.add(ball);
            }
        }
        return balls;
    }
}
