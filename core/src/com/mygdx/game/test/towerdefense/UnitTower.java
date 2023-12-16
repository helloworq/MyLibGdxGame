package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ball.Ball;
import com.mygdx.game.test.towerdefense.util.ComonUtils;

import java.util.concurrent.CopyOnWriteArrayList;

public class UnitTower extends Sprite {
    private int                        attackSize;
    private Color                      color;
    private Node                       mapOriginPosition;//自定义地图中二维数组地图的位置数据
    private Node                       gameOriginPosition;//游戏中的位置数据（没有和地图长宽相乘的数据）
    private Node                       gameFinalPosition;//游戏中的位置数据
    private CopyOnWriteArrayList<Ball> bullets = new CopyOnWriteArrayList<>();
    private float                      cd;

    public UnitTower(float x, float y, Color color, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = getTexture().getWidth();
        this.color = color;
    }

    public UnitTower(float x, float y, int attackSize, Color color, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = attackSize;
        this.color = color;
    }

    public void draw(Batch batch) {
        super.draw(batch);

        //https://github.com/libgdx/libgdx/issues/1186 shaperender和spritebatch同时渲染时会冲突
        //绘制攻击范围圈
        Pixmap attackArea = new Pixmap(attackSize, attackSize, Pixmap.Format.RGBA8888);
        attackArea.setColor(Color.WHITE);
        attackArea.drawCircle(attackSize / 2, attackSize / 2, attackSize / 2);
        Texture bgTexture = new Texture(attackArea);
        batch.draw(bgTexture, getX(),getY());

        //绘制子弹
        for (Ball ball : bullets) {
            Pixmap bullet = new Pixmap(getTexture().getWidth(), getTexture().getWidth(), Pixmap.Format.RGBA8888);
            bullet.setColor(Color.WHITE);
            bullet.drawCircle(getTexture().getWidth() / 2, getTexture().getWidth() / 2, getTexture().getWidth() / 2);
            Texture bulletTexture = new Texture(bullet);
            batch.draw(bulletTexture, ball.x,ball.y);
        }
    }

    public void attack(float deg) {
        Ball ball = new Ball(
                (int) getX(),
                (int) getY(),
                getTexture().getWidth(),
                (10f * ComonUtils.cos(deg)),
                (10f * ComonUtils.sin(deg)),
                Color.BLUE);
        bullets.add(ball);
    }

    //getter setter
    public CopyOnWriteArrayList<Ball> getBullets() {
        return bullets;
    }

    public void setBullets(CopyOnWriteArrayList<Ball> bullets) {
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
