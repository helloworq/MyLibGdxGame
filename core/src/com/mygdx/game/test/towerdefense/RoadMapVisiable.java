package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class RoadMapVisiable extends ApplicationAdapter {
    ShapeRenderer shape;
    int width;
    int height;
    int count = 60;
    List<UnitTower> unitTowerList;
    List<Node> nodes;
    UnitTower hero;
    int step = 0;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        width = Gdx.graphics.getWidth() - 40;
        height = Gdx.graphics.getHeight() - 40;
        unitTowerList = RoadMapTransformer.transform(width, height);

        nodes = RoadMap.findPath();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);


        for (UnitTower b : unitTowerList) {
            shape.setColor(b.color);
            shape.circle(b.x + 20, b.y + 20, b.size);
        }

        if (hero != null) {
            shape.setColor(Color.WHITE);
            shape.circle(hero.gameFinalPosition.x + 20,
                    hero.gameFinalPosition.y + 20, hero.size);
        }
        if (count <= 0 && step < nodes.size()) {
            Node node = nodes.get(step);
            for (UnitTower tower : unitTowerList) {
                if (tower.mapOriginPosition.x == node.x &&
                        tower.mapOriginPosition.y == node.y) {
                    hero = tower;
                    break;
                }
            }
            count = 60;
            step++;
        }
        count--;

        shape.end();
    }
}
