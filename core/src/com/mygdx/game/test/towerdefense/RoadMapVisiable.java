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
import com.mygdx.game.test.towerdefense.constant.TowerConstant;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadMapVisiable extends ApplicationAdapter {
    SpriteBatch                batch;
    ShapeRenderer              shape;
    int                        width;
    int                        height;
    int                        count   = 20;
    List<UnitTower>            unitTowerList;
    List<Node>                 nodes;
    UnitTower                  ghost;
    int                        step    = 0;
    boolean                    start   = false;
    int                        count2  = 5;
    int                        bx      = 208;
    int                        by      = 335;
    float                      fixDeg  = 45f;//普通图片有45°的倾角，在此进行补足
    CopyOnWriteArrayList<Ball> balls   = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Ball> bullets = new CopyOnWriteArrayList<>();
    UnitTower                  building;

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

        building = new UnitTower(bx, by, TowerConstant.NORMAL_TOWER_ATTACK_RANGE, Color.WHITE, "tower/sword.png");

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
            shape.setColor(b.getColor());
            shape.circle(b.getX(), b.getY(), b.getTexture().getWidth());
        }

        //绘制怪物
        if (ghost != null) {
            shape.setColor(Color.WHITE);
            shape.circle(ghost.getGameFinalPosition().x,
                    ghost.getGameFinalPosition().y, ghost.getTexture().getWidth());
        }
        if (count <= 0 && step < nodes.size() && start) {
            Node node = nodes.get(step);
            for (UnitTower tower : unitTowerList) {
                if (tower.getMapOriginPosition().x == node.x &&
                        tower.getMapOriginPosition().y == node.y) {
                    ghost = tower;
                    break;
                }
            }
            count = 20;
            step++;
        }
        count--;

        //绘制炮塔
        shape.setColor(Color.GOLD);
        if (ghost != null) {
            if (ComonUtils.distance(ghost.getGameFinalPosition().x, ghost.getGameFinalPosition().y, bx, by)
                    < (ghost.getAttackSize() + building.getAttackSize())) {
                shape.setColor(Color.BROWN);
                float d = (float) ((Math.atan2(ghost.getGameFinalPosition().y - by, ghost.getGameFinalPosition().x - bx)) * (180 / Math.PI));
                building.setRotation(d - fixDeg);

                attack(bx, by, d - fixDeg);
            }

            shape.circle(bx, by, building.getAttackSize());
        }
        batch.begin();
        building.draw(batch);
        batch.end();

        shape.end();
    }
}
