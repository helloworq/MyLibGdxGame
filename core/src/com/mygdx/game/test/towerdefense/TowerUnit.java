package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ball.Ball;
import com.mygdx.game.test.towerdefense.util.Bullet;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 炮塔 怪物 地图的基类
 */
public class TowerUnit extends Sprite {
    private int                          attackSize;
    private Color                        color;
    private Node                         mapOriginPosition;//自定义地图中二维数组地图的位置数据
    private Node                         gameOriginPosition;//游戏中的位置数据（没有和地图长宽相乘的数据）
    private Node                         gameFinalPosition;//游戏中的位置数据
    private CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private float                        cd;
    private Pixmap                       attackArea;

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
    }

    public void draw(Batch batch) {
        super.draw(batch);

        //https://github.com/libgdx/libgdx/issues/1186 shaperender和spritebatch同时渲染时会冲突
        //绘制攻击范围圈
        Texture bgTexture = new Texture(attackArea);
        //batch.draw(bgTexture, getX(), getY());
        batch.draw(bgTexture, (-attackSize / 2 + getX()), (-attackSize / 2 + getY()));

        //绘制子弹
        for (Bullet ball : bullets) {
            batch.draw(ball.getTexture(), ball.getX(), ball.getY());
            ball.update();
        }
    }

    public void attack(float deg) {
        Bullet ball = new Bullet(
                (int) getX(),
                (int) getY(),
                (10f * ComonUtils.cos(deg)),
                (10f * ComonUtils.sin(deg)),
                "tower/scourge.png");
        bullets.add(ball);
    }

    //getter setter
    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(CopyOnWriteArrayList<Bullet> bullets) {
        this.bullets = bullets;
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
