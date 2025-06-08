package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 炮塔 怪物 地图的基类
 */
public class TowerUnit extends Sprite {
    private int                              attackSize;
    private Color                            color;
    private Node                             mapOriginPosition;//自定义地图中二维数组地图的位置数据
    private Node                             gameOriginPosition;//游戏中的位置数据（没有和地图长宽相乘的数据）
    private Node                             gameFinalPosition;//游戏中的位置数据
    private CopyOnWriteArrayList<BulletUnit> bulletUnits = new CopyOnWriteArrayList<>();
    private float                            cd          = 0.5f;
    private float                            currentTime = 0f;
    private Pixmap                           attackArea;
    private Texture                          attackAreaTexture;
    private float                            fixDeg = 45f;//普通图片有45°的倾角，在此进行补足

    public TowerUnit(float x, float y, Color color, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = getTexture().getWidth();
        this.color = color;
    }

    public TowerUnit(float x, float y, int attackSize, Color color, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = attackSize;
        this.color = color;
        this.attackArea = new Pixmap(attackSize, attackSize, Pixmap.Format.RGBA8888);

        attackArea.setColor(Color.WHITE);
        attackArea.drawCircle(attackSize / 2, attackSize / 2, attackSize / 2);
        attackAreaTexture = new Texture(attackArea);
    }

    public void draw(Batch batch) {
        super.draw(batch);

        //https://github.com/libgdx/libgdx/issues/1186 shaperender和spritebatch同时渲染时会冲突
        //绘制攻击范围圈
        batch.draw(attackAreaTexture, (-attackSize / 2f + getX()), (-attackSize / 2f + getY()));

        //绘制子弹
        List<BulletUnit> toDelete = new CopyOnWriteArrayList<>();
        for (BulletUnit bulletUnit : bulletUnits) {
            batch.draw(bulletUnit.getTexture(), bulletUnit.getX(), bulletUnit.getY());
            toDelete.add(bulletUnit.update());
        }
        bulletUnits.removeAll(toDelete);
        currentTime += Gdx.graphics.getDeltaTime();
    }

    public void attack(float deg) {
        if (currentTime - Gdx.graphics.getDeltaTime() > cd) {
            setRotation(deg - fixDeg);
            BulletUnit ball = new BulletUnit(
                    (int) getX(),
                    (int) getY(),
                    (5f * ComonUtils.cos(deg)),
                    (5f * ComonUtils.sin(deg)),
                    "tower/scourge.png");
            bulletUnits.add(ball);
            currentTime = 0f;
        }
    }

    //getter setter
    public CopyOnWriteArrayList<BulletUnit> getBullets() {
        return bulletUnits;
    }

    public void setBullets(CopyOnWriteArrayList<BulletUnit> bulletUnits) {
        this.bulletUnits = bulletUnits;
    }

    public int getAttackSize() {
        return attackSize;
    }

    public void setAttackSize(int attackSize) {
        this.attackSize = attackSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Node getMapOriginPosition() {
        return mapOriginPosition;
    }

    public void setMapOriginPosition(Node mapOriginPosition) {
        this.mapOriginPosition = mapOriginPosition;
    }

    public Node getGameOriginPosition() {
        return gameOriginPosition;
    }

    public void setGameOriginPosition(Node gameOriginPosition) {
        this.gameOriginPosition = gameOriginPosition;
    }

    public Node getGameFinalPosition() {
        return gameFinalPosition;
    }

    public void setGameFinalPosition(Node gameFinalPosition) {
        this.gameFinalPosition = gameFinalPosition;
    }
}
