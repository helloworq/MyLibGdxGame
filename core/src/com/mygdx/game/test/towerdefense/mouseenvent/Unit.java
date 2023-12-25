package com.mygdx.game.test.towerdefense.mouseenvent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Unit extends Sprite {
    private ClickEvent consumer;
    public int        id;

    public Unit(Texture texture, int x, int y, int id) {
        super(texture);
        setPosition(x, y);
        this.id = id;
    }

    public ClickEvent getConsumer() {
        return consumer;
    }

    public void onClick(ClickEvent event) {
        this.consumer = event;
    }
}
