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
            {RODE, WALL, WALL, WALL, WALL, RODE},
            {RODE, WALL, WALL, WALL, WALL, RODE},
            {RODE, WALL, WALL, RODE, RODE, RODE},
            {RODE, WALL, WALL, RODE, WALL, WALL},
            {RODE, RODE, RODE, RODE, WALL, WALL}
    };
    List<Node> nodes = new LinkedList<>();

    static class Node {
        public int x;
        public int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        new RoadMap().run();
    }

    private void run() {
        findPath(MAP, 0, 0);
        for (Node n : nodes) {
            System.out.println(n.x + " " + n.y);
        }
    }

    private void findPath(int[][] map, int startX, int startY) {
        Node node = new Node(startX, startY);
        nodes.add(node);
        map[startX][startY] = VISITED;
        int x1 = startX - 1;
        int x2 = startX + 1;
        int y1 = startY - 1;
        int y2 = startY + 1;
        if (x1 >= 0 && map[x1][startY] == RODE) {
            findPath(map, x1, startY);
        } else if (x2 < X && map[x2][startY] == RODE) {
            findPath(map, x2, startY);
        } else if (y1 >= 0 && map[startX][y1] == RODE) {
            findPath(map, startX, y1);
        } else if (y2 < Y && map[startX][y2] == RODE) {
            findPath(map, startX, y2);
        }
    }
}
