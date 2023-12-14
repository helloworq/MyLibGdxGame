package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.ball.Ball;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadMapVisiable extends ApplicationAdapter {
    SpriteBatch     batch;
    ShapeRenderer   shape;
    int             width;
    int             height;
    int             count = 20;
    List<UnitTower> unitTowerList;
    List<Node>      nodes;
    UnitTower       hero;
    int             step  = 0;
    boolean         start = false;

    int                        count2  = 5;
    int                        bx      = 150;
    int                        by      = 100;
    float                      fixDeg  = 45f;//普通图片有45°的倾角，在此进行补足
    CopyOnWriteArrayList<Ball> balls   = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Ball> bullets = new CopyOnWriteArrayList<>();
    Sprite                     building;

    private void attack(float x, float y, float deg) {
        if (count2 <= 0) {
            Ball ball = new Ball(bx, by, 10,
                    (5f * ComonUtils.cos(deg + fixDeg)),
                    (5f * ComonUtils.sin(deg + fixDeg)),
                    Color.BLUE);
            bullets.add(ball);
            count2 = 1;
        }
        count2--;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        unitTowerList = RoadMapTransformer.transform(width, height);

        nodes = RoadMap.findPath();

        building = new Sprite(new Texture(Gdx.files.internal("tower/sword.png")));
        building.setPosition(bx - building.getWidth() / 2, by - building.getHeight() / 2);
        building.setOriginCenter();

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
        shape.begin(ShapeRenderer.ShapeType.Line);

        for (Ball ball : balls) {
            for (Ball bullet : bullets) {
                if (ComonUtils.distance(bullet.x, bullet.y, ball.x, ball.y) < (ball.size + bullet.size)) {
                    bullets.remove(bullet);
                    balls.remove(ball);
                }
            }
        }

        //绘制子弹
        for (Ball ball : bullets) {
            shape.setColor(ball.color);
            ball.update();
            ball.draw(shape);
        }

        //绘制地图
        for (UnitTower b : unitTowerList) {
            shape.setColor(b.color);
            shape.circle(b.x, b.y, b.size);
        }

        //绘制怪物
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
            count = 20;
            step++;
        }
        count--;

        //绘制炮塔
        shape.setColor(Color.GOLD);
        if (hero != null) {
            if (ComonUtils.distance(hero.gameFinalPosition.x, hero.gameFinalPosition.y, bx, by) < (hero.size + 200)) {
                shape.setColor(Color.BROWN);
                float d = (float) ((Math.atan2(hero.gameFinalPosition.y - by, hero.gameFinalPosition.x - bx)) * (180 / Math.PI));
                building.setRotation(d - fixDeg);

                attack(bx, by, d - fixDeg);
            }

            shape.circle(bx, by, 200);
            batch.begin();
            building.draw(batch);
            batch.end();
        }
        shape.end();
    }
}
