package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BulletUnit extends Sprite {
    public float xSpeed;
    public float ySpeed;

    public BulletUnit(float x, float y, float xSpeed, float ySpeed, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update() {
        setPosition(getX() + xSpeed, getY() + ySpeed);

        if (getX() < 0 || getX() > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed;
        }
        if (getY() < 0 || getY() > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }
    }
}
