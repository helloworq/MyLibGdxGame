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

public class MouseEvent2 extends ApplicationAdapter {
    SpriteBatch      batch;
    int              x;
    int              y;
    List<Unit>       curLayer;
    List<Unit>       map     = new ArrayList<>();
    List<Unit>       options = new ArrayList<>();
    Unit             sword;
    Unit             necromancy;
    Unit             scourge;
    EventChain<Unit> eventChain;

    @Override
    public void create() {
        batch = new SpriteBatch();
        eventChain = new EventChain<>();

        for (int i = 0; i < 10; i++) {
            int x1 = i * 32;
            Unit u = new Unit(new Texture(Gdx.files.internal("tower/transmutation.png")),
                    100 + x1, 100, i, 0);
            if (i % 2 == 0) {
                u.onClick((x, y, clickOptionList, batch) -> {
                    int j = 0;
                    for (Unit t : clickOptionList) {
                        t.setPosition(x + (j * t.getWidth()), y);
                        t.draw(batch);
                        j++;
                    }
                    curLayer = options;//当前图层设置为options
                });
            }
            map.add(u);
        }

        //options的按钮设置事件和样式
        sword = new Unit(new Texture(Gdx.files.internal("tower/sword.png")), 999);
        sword.onClick((x, y, textureList, batch) -> {
            eventChain.getRootEvent().setTexture(sword.getTexture());
            eventChain.clear();
            curLayer = map;
        });
        necromancy = new Unit(new Texture(Gdx.files.internal("tower/necromancy.png")), 999);
        necromancy.onClick((x, y, textureList, batch) -> {
            eventChain.getRootEvent().setTexture(necromancy.getTexture());
            eventChain.clear();
            curLayer = map;
        });
        scourge = new Unit(new Texture(Gdx.files.internal("tower/scourge.png")), 999);
        scourge.onClick((x, y, textureList, batch) -> {
            eventChain.getRootEvent().setTexture(scourge.getTexture());
            eventChain.clear();
            curLayer = map;
        });
        options = Arrays.asList(scourge, sword, necromancy);

        curLayer = map;
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            //点击事件,可能是地图元素也可能是options元素
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                x = screenX;
                y = Gdx.graphics.getHeight() - screenY;

                boolean find = false;
                for (Unit unit : curLayer) {
                    if (y > unit.getY() && y < (unit.getY() + unit.getHeight())
                            && x > unit.getX() && x < (unit.getX() + unit.getWidth())
                            && unit.getConsumer() != null) {
                        //如果当前点击位置和当前图层有重合也就是确认了点击的单元，则将此单元推入eventChain
                        eventChain.pushEvent(unit);
                        find = true;
                    }
                }
                if (!find) {
                    curLayer = map;
                    eventChain.clear();
                }

                return true;
            }
        });
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (Unit unit : map) {
            unit.draw(batch);
        }

        if (eventChain.canExecute()) {
            eventChain.getTopEvent().getConsumer().react(x, y, options, batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
