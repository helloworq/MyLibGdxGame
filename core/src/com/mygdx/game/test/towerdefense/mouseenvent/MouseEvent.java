package com.mygdx.game.test.towerdefense.mouseenvent;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MouseEvent extends ApplicationAdapter {
    SpriteBatch batch;
    int         x;
    int         y;
    List<Unit>  map  = new ArrayList<>();
    Texture     sword;
    Texture     necromancy;
    Texture     scourge;
    Unit        base;
    boolean     show = false;

    @Override
    public void create() {
        batch = new SpriteBatch();

        for (int i = 0; i < 10; i++) {
            int  x1 = i * 32;
            Unit u  = new Unit(new Texture(Gdx.files.internal("tower/transmutation.png")), 100 + x1, 100, i);
            if (i % 2 == 0) {
                u.onClick((x, y, textureList, batch) -> {
                    int j = 0;
                    for (Texture t : textureList) {
                        batch.draw(t, x + (j * t.getWidth()), y);
                        j++;
                    }
                });
            }
            map.add(u);
        }

        sword = new Texture(Gdx.files.internal("tower/sword.png"));
        necromancy = new Texture(Gdx.files.internal("tower/necromancy.png"));
        scourge = new Texture(Gdx.files.internal("tower/scourge.png"));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //touchDownEvent(screenX, screenY);
                x = screenX;
                y = Gdx.graphics.getHeight() - screenY;
                show = !show;

                //double angle = Math.atan2((y - 100d), (x - 100d)) * (180d / Math.PI);
                boolean find = false;
                for (Unit unit : map) {
                    if (y > unit.getY() && y < (unit.getY() + unit.getHeight())
                            && x > unit.getX() && x < (unit.getX() + unit.getWidth())) {
                        base = unit;
                        find = true;
                    }
                }
                if (!find) {
                    base = null;
                }

                return true;
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(Math.atan2((1d), (-1d)) * (180d / Math.PI));
    }

    private void touchDownEvent(int x, int y) {
        System.out.println(x + "  " + y);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //batch.draw(base, 100, 100);

        for (Unit unit : map) {
            unit.draw(batch);
        }

        if (base != null && base.getConsumer() != null) {
            base.getConsumer().react(x, y, Arrays.asList(scourge, sword, necromancy), batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
