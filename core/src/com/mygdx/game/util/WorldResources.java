package com.mygdx.game.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class WorldResources {
    public static final float GRAVITY = 9.8f;
    public static final World WORLD = new World(new Vector2(0, -GRAVITY), true);
}
