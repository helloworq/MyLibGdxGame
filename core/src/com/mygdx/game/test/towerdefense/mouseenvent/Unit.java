package com.mygdx.game.test.towerdefense.mouseenvent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Unit extends Sprite {
    private ClickEvent consumer;
    public  int        id;
    public  int        layer;//当前图层

    public Unit(Texture texture, int layer) {
        super(texture);
        this.layer = layer;
    }

    public Unit(Texture texture, int x, int y, int id, int layer) {
        super(texture);
        setPosition(x, y);
        this.id = id;
        this.layer = layer;
    }

    public ClickEvent getConsumer() {
        return consumer;
    }

    public void onClick(ClickEvent event) {
        this.consumer = event;
    }
}
