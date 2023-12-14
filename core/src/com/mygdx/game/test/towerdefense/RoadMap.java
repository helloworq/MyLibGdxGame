package com.mygdx.game.test.towerdefense;

import java.util.LinkedList;
import java.util.List;

public class RoadMap {
    private static final int VISITED = -1;
    private static final int WALL = 0;
    private static final int RODE = 1;
    int X = 5;
    int Y = 6;
    int[][] MAP = new int[][]{
            {RODE, WALL, RODE, RODE, RODE, RODE},
            {RODE, WALL, RODE, WALL, WALL, RODE},
            {RODE, WALL, WALL, RODE, RODE, RODE},
            {RODE, WALL, WALL, RODE, WALL, WALL},
            {RODE, RODE, RODE, RODE, WALL, WALL}
    };

    static class Node {
        public int x;
        public int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        new RoadMap().findPath();
    }

    private void findPath() {
        List<Node> nodes = new LinkedList<>();
        doFind(MAP, 0, 0, nodes);
        for (Node n : nodes) {
            System.out.println(n.x + " " + n.y);
        }
    }

    private List<Node> doFind(int[][] map, int startX, int startY, List<Node> nodes) {
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
        return nodes;
    }
}
