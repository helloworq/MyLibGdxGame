package com.mygdx.game.test.towerdefense;

import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RoadMap {
    static final int     VISITED = -1;
    static final int     WALL    = 0;
    static final int     RODE    = 1;
    static       int     X       = 15;
    static       int     Y       = 20;
    static       int[][] MAP     = new int[][]{
            {RODE, WALL, RODE, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, RODE, RODE, RODE, RODE, RODE, RODE, RODE, WALL, WALL},
            {RODE, WALL, RODE, WALL, RODE, RODE, RODE, WALL, RODE, RODE, RODE, RODE, WALL, WALL, WALL, WALL, WALL, RODE, RODE, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, WALL, WALL, RODE, RODE, RODE, WALL, WALL, WALL, WALL, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, RODE, RODE, WALL, RODE, RODE, RODE, RODE, RODE, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, RODE, RODE, RODE, WALL, RODE, RODE, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, WALL, RODE, WALL, RODE, WALL, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, RODE, RODE, WALL, WALL, RODE, RODE, RODE, WALL, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, RODE, RODE, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, WALL, WALL, RODE, WALL, WALL, WALL, RODE, RODE, RODE, RODE, RODE, RODE, RODE},
            {RODE, WALL, RODE, WALL, RODE, WALL, RODE, RODE, WALL, RODE, RODE, RODE, WALL, RODE, WALL, WALL, WALL, WALL, WALL, WALL},
            {RODE, WALL, RODE, WALL, RODE, WALL, WALL, RODE, WALL, WALL, WALL, RODE, WALL, RODE, WALL, RODE, RODE, RODE, WALL, WALL},
            {RODE, WALL, RODE, RODE, RODE, WALL, WALL, RODE, RODE, RODE, RODE, RODE, WALL, RODE, RODE, RODE, WALL, RODE, RODE, RODE},
            {RODE, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, RODE},
            {RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE, RODE,},
    };

    public static void main(String[] args) {
        findPath(null, 0);
    }

    public static List<Node> findPath(List<TileUnit> tileMapSets, int split) {
        List<Node> nodes = new LinkedList<>();
        int[][]    map   = ComonUtils.deepCopy(MAP);

        doFind(map, 0, 0, nodes);

        List<Node> detailNodes = new LinkedList<>();
        //将map坐标转化为细粒度的像素坐标
        TileUnit currentTile, nextTile;
        for (int i = 0; i < nodes.size() - 1; i++) {
            //tile node
            currentTile = findTile(tileMapSets, nodes.get(i));
            nextTile = findTile(tileMapSets, nodes.get(i + 1));
            if (currentTile.getGameFinalPosition().x != nextTile.getGameFinalPosition().x) {
                float splitWidth = (nextTile.getGameFinalPosition().x - currentTile.getGameFinalPosition().x) / split;
                for (int j = 0; j < split; j++) {
                    Node node = new Node(currentTile.getGameFinalPosition().x + (j * splitWidth), currentTile.getGameFinalPosition().y);
                    detailNodes.add(node);
                }
            } else if (currentTile.getGameFinalPosition().y != nextTile.getGameFinalPosition().y) {
                float splitWidth = (nextTile.getGameFinalPosition().y - currentTile.getGameFinalPosition().y) / split;
                for (int j = 0; j < split; j++) {
                    Node node = new Node(currentTile.getGameFinalPosition().x, currentTile.getGameFinalPosition().y + (j * splitWidth));
                    detailNodes.add(node);
                }
            }
        }
        return detailNodes;
    }

    private static TileUnit findTile(List<TileUnit> tileMapSets, Node node) {
        for (TileUnit tower : tileMapSets) {
            if (tower.getMapOriginPosition().x == node.x &&
                    tower.getMapOriginPosition().y == node.y) {
                return tower;
            }
        }
        return null;
    }

    //public static void transformer

    private static void doFind(int[][] map, int startX, int startY, List<Node> nodes) {
        Node node = new Node(startX, startY);
        nodes.add(node);
        map[startX][startY] = VISITED;
        int x1 = startX - 1;
        int x2 = startX + 1;
        int y1 = startY - 1;
        int y2 = startY + 1;
        if (x1 >= 0 && map[x1][startY] == RODE) {
            doFind(map, x1, startY, nodes);
        } else if (x2 < X && map[x2][startY] == RODE) {
            doFind(map, x2, startY, nodes);
        } else if (y1 >= 0 && map[startX][y1] == RODE) {
            doFind(map, startX, y1, nodes);
        } else if (y2 < Y && map[startX][y2] == RODE) {
            doFind(map, startX, y2, nodes);
        }
    }
}
