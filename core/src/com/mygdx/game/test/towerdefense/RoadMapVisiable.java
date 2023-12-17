package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ball.Ball;
import com.mygdx.game.test.towerdefense.constant.TowerConstant;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadMapVisiable extends ApplicationAdapter {
    SpriteBatch    batch;
    int            width;
    int            height;
    int            count  = 20;
    List<TileUnit> tileMapSets;
    List<Node>     rodeNodeSets;
    GhostUnit      ghost;
    TowerUnit      arrowTower;
    int            step   = 0;
    boolean        start  = false;
    int            bx     = 208;
    int            by     = 335;
    float          fixDeg = 45f;//普通图片有45°的倾角，在此进行补足

    @Override
    public void create() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth() - 20;
        height = Gdx.graphics.getHeight() - 20;

        tileMapSets = RoadMapTransformer.transform(width, height);
        rodeNodeSets = RoadMap.findPath();

        arrowTower = new TowerUnit(bx, by, TowerConstant.NORMAL_TOWER_ATTACK_RANGE, Color.WHITE, "tower/sword.png");
        ghost = new GhostUnit(0, 0, Color.WHITE, "tower/sword.png");

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

        batch.begin();
        //绘制地图
        for (TileUnit b : tileMapSets) {
            batch.draw(b.getTexture(), b.getX(), b.getY());
        }

        //ComonUtils.onCollision(balls, bullets);

        //绘制怪物
        if (ghost != null) {
            batch.draw(ghost.getTexture(), ghost.getX(), ghost.getY());
        }
        if (count <= 0 && step < rodeNodeSets.size() && start) {
            Node node = rodeNodeSets.get(step);
            for (TileUnit tower : tileMapSets) {
                if (tower.getMapOriginPosition().x == node.x &&
                        tower.getMapOriginPosition().y == node.y) {
                    ghost.setX(tower.getGameFinalPosition().x);
                    ghost.setY(tower.getGameFinalPosition().y);
                    break;
                }
            }
            count = 20;
            step++;
        }
        count--;

        //绘制炮塔
        if (ghost != null) {
            if (ComonUtils.distance(ghost.getX(), ghost.getY(), bx, by) < (ghost.getAttackSize() + arrowTower.getAttackSize() / 2f)) {
                float d = (float) ((Math.atan2(ghost.getY() - by, ghost.getX() - bx)) * (180 / Math.PI));
                arrowTower.setRotation(d - fixDeg);
                arrowTower.attack(d);
            }
        }

        arrowTower.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
