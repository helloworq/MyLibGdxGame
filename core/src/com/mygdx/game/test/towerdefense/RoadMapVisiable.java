package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class RoadMapVisiable extends ApplicationAdapter {
    ShapeRenderer   shape;
    int             width;
    int             height;
    int             count = 5;
    List<UnitTower> unitTowerList;
    List<Node>      nodes;
    UnitTower       hero;
    int             step  = 0;

    boolean start = false;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        unitTowerList = RoadMapTransformer.transform(width, height);

        nodes = RoadMap.findPath();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                start = true;
                return true;
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);


        for (UnitTower b : unitTowerList) {
            shape.setColor(b.color);
            shape.circle(b.x, b.y, b.size);
        }

        if (hero != null) {
            shape.setColor(Color.WHITE);
            shape.circle(hero.gameFinalPosition.x,
                    hero.gameFinalPosition.y, hero.size);
        }
        if (count <= 0 && step < nodes.size() && start) {
            Node node = nodes.get(step);
            for (UnitTower tower : unitTowerList) {
                if (tower.mapOriginPosition.x == node.x &&
                        tower.mapOriginPosition.y == node.y) {
                    hero = tower;
                    break;
                }
            }
            count = 5;
            step++;
        }
        count--;

        shape.end();
    }
}
