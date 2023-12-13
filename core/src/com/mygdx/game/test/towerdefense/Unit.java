package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.graphics.Color;

public class Unit {
    public int x;
    public int y;
    public int size;
    public Color color;
    public int speed;

    public Unit(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }
}
