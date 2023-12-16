package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class RoadMapTransformer {
    private static final int ballSize = 10;

    public static List<TowerUnit> transform(int width, int height) {
        List<TowerUnit> balls       = new ArrayList<>();
        int             widthSplit  = width / (RoadMap.Y);
        int             heightSplit = height / (RoadMap.X);

        for (int i = 0; i < RoadMap.X; i++) {
            for (int j = 0; j < RoadMap.Y; j++) {
                TowerUnit ball;
                if (RoadMap.MAP[i][j] == RoadMap.RODE) {
                    ball = new TowerUnit(
                            heightSplit * j + ballSize,
                            widthSplit * (RoadMap.X - i) + ballSize,
                            Color.SLATE,
                            "tower/transmutation.png"
                    );
                } else {
                    ball = new TowerUnit(
                            heightSplit * j + ballSize,
                            widthSplit * (RoadMap.X - i) + ballSize,
                            Color.BROWN,
                            "tower/necromancy.png"
                    );
                }
                ball.setMapOriginPosition(new Node(i, j));
                ball.setGameOriginPosition(new Node(j, RoadMap.X - i));
                ball.setGameFinalPosition(new Node(heightSplit * j + ballSize, widthSplit * (RoadMap.X - i) + ballSize));
                balls.add(ball);
            }
        }
        return balls;
    }
}
