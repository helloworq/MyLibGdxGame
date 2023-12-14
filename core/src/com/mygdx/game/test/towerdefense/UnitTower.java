package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;

public class UnitTower {
    public int x;
    public int y;//和地图长宽相乘的数据
    public Node gameFinalPosition;//游戏中的位置数据
    public int size;
    public double xSpeed;
    public double ySpeed;
    public Color color;
    public Node mapOriginPosition;//自定义地图中二维数组地图的位置数据
    public Node gameOriginPosition;//游戏中的位置数据（没有和地图长宽相乘的数据）

    public Node getMapOriginPosition() {
        return mapOriginPosition;
    }

    public void setMapOriginPosition(Node mapOriginPosition) {
        this.mapOriginPosition = mapOriginPosition;
    }

    public Node getGameOriginPosition() {
        return gameOriginPosition;
    }

    public void setGameOriginPosition(Node gameOriginPosition) {
        this.gameOriginPosition = gameOriginPosition;
    }

    public Node getGameFinalPosition() {
        return gameFinalPosition;
    }

    public void setGameFinalPosition(Node gameFinalPosition) {
        this.gameFinalPosition = gameFinalPosition;
    }

    public UnitTower(int x, int y, int size, double xSpeed, double ySpeed, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }
}
