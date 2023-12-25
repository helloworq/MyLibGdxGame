package com.mygdx.game.test.towerdefense.mouseenvent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface ClickEvent {
    void react(int x, int y, List<Texture> textureList, SpriteBatch batch);
}
