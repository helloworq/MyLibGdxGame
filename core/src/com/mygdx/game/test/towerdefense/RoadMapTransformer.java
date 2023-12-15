package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.test.towerdefense.constant.TowerConstant;

import java.util.ArrayList;
import java.util.List;

public class RoadMapTransformer {
    private static final int ballSize = 10;

    public static List<UnitTower> transform(int width, int height) {
        List<UnitTower> balls       = new ArrayList<>();
        int             widthSplit  = width / (RoadMap.Y);
        int             heightSplit = height / (RoadMap.X);

        for (int i = 0; i < RoadMap.X; i++) {
            for (int j = 0; j < RoadMap.Y; j++) {
                UnitTower ball;
                if (RoadMap.MAP[i][j] == RoadMap.RODE) {
                    ball = new UnitTower(
                            heightSplit * j + ballSize,
                            widthSplit * (RoadMap.X - i) + ballSize,
                            Color.SLATE,
                            "tower/transmutation.png"
                    );
                } else {
                    ball = new UnitTower(
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
