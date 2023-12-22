package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.test.towerdefense.constant.TowerConstant;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoadMapVisiable extends ApplicationAdapter {
    SpriteBatch                     batch;
    int                             width;
    int                             height;
    List<TileUnit>                  tileMapSets;
    List<Node>                      rodeNodeSets;
    CopyOnWriteArrayList<GhostUnit> ghosts;
    TowerUnit                       arrowTower;
    boolean                         start  = false;
    float                           fixDeg = 45f;//普通图片有45°的倾角，在此进行补足

    @Override
    public void create() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth() - 20;
        height = Gdx.graphics.getHeight() - 20;

        tileMapSets = RoadMapTransformer.transform(width, height);
        rodeNodeSets = RoadMap.findPath(tileMapSets, 100);

        arrowTower = new TowerUnit(208f, 335f, TowerConstant.NORMAL_TOWER_ATTACK_RANGE, Color.WHITE, "tower/sword.png");
        ghosts = new CopyOnWriteArrayList<>(Arrays.asList(new GhostUnit(0, 0, Color.WHITE, "tower/sword.png")));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                start = true;
                touchDownEvent(screenX, screenY, tileMapSets);
                return true;
            }
        });
    }

    private void touchDownEvent(int x, int y, List<TileUnit> tileMapSets) {
        ghosts.add(new GhostUnit(0, 0, Color.WHITE, "tower/sword.png"));

        //System.out.println(x + "  " + y + "  " + (Gdx.graphics.getHeight() - y));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //绘制地图
        for (TileUnit b : tileMapSets) {
            b.draw(batch);
        }

        ComonUtils.onCollision(ghosts, arrowTower.getBullets());
        //绘制怪物
        if (start) {
            for (GhostUnit ghost : ghosts) {
                ghost.move(rodeNodeSets);
                ghost.draw(batch);
            }
        }

        //绘制炮塔
        for (GhostUnit ghost : ghosts) {
            if (ComonUtils.distance(ghost.getX(), ghost.getY(), arrowTower.getX(), arrowTower.getY())
                    < (ghost.getAttackSize() + arrowTower.getAttackSize() / 2f)) {
                float d = (float) ((Math.atan2(ghost.getY() - arrowTower.getX(),
                        ghost.getX() - arrowTower.getY())) * (180 / Math.PI));
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
