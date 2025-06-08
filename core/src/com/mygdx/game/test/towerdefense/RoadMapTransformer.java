package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class RoadMapTransformer {
    private static final int ballSize = 10;

    public static List<TileUnit> transform(int width, int height) {
        List<TileUnit> tiles       = new ArrayList<>();
        int            widthSplit  = width / (RoadMap.Y);
        int            heightSplit = height / (RoadMap.X);

        for (int i = 0; i < RoadMap.X; i++) {
            for (int j = 0; j < RoadMap.Y; j++) {
                TileUnit tile;
                if (RoadMap.MAP[i][j] == RoadMap.RODE) {
                    tile = new TileUnit(
                            heightSplit * j + ballSize,
                            widthSplit * (RoadMap.X - i) + ballSize,
                            Color.SLATE,
                            "tower/transmutation.png"
                    );
                } else {
                    tile = new TileUnit(
                            heightSplit * j + ballSize,
                            widthSplit * (RoadMap.X - i) + ballSize,
                            Color.BROWN,
                            "tower/necromancy.png"
                    );
                }
                tile.setMapOriginPosition(new Node(i, j));
                tile.setGameOriginPosition(new Node(j, RoadMap.X - i));
                tile.setGameFinalPosition(new Node(heightSplit * j + ballSize, widthSplit * (RoadMap.X - i) + ballSize));
                tiles.add(tile);
            }
        }
        return tiles;
    }
}
