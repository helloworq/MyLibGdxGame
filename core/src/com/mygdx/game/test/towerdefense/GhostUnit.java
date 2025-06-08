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

public class GhostUnit extends Sprite {
    private int                              attackSize;
    private Node                             mapOriginPosition;//自定义地图中二维数组地图的位置数据
    private Node                             gameOriginPosition;//游戏中的位置数据（没有和地图长宽相乘的数据）
    private Node                             gameFinalPosition;//游戏中的位置数据
    private CopyOnWriteArrayList<BulletUnit> bulletUnits = new CopyOnWriteArrayList<>();
    private float                            cd;
    private Pixmap                           attackArea;
    private float                            speed       = 1f;
    private int                              step        = 0;

    public GhostUnit(float x, float y, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = getTexture().getWidth();
    }

    public GhostUnit(float x, float y, int attackSize, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        setPosition(x, y);
        this.attackSize = attackSize;
        this.attackArea = new Pixmap(attackSize, attackSize, Pixmap.Format.RGBA8888);

        attackArea.setColor(Color.WHITE);
        attackArea.drawCircle(attackSize / 2, attackSize / 2, attackSize / 2);
    }

    public void move(List<Node> rodeNodeSets) {
        Node node = rodeNodeSets.get(Math.min(step, rodeNodeSets.size() - 1));
        setX(node.x);
        setY(node.y);
        step++;
    }

    public void draw(Batch batch) {
        super.draw(batch);
        //绘制子弹
        for (BulletUnit ball : bulletUnits) {
            batch.draw(ball.getTexture(), ball.getX(), ball.getY());
            ball.update();
        }
    }

    public void attack(float deg) {
        BulletUnit ball = new BulletUnit(
                (int) getX(),
                (int) getY(),
                (10f * ComonUtils.cos(deg)),
                (10f * ComonUtils.sin(deg)),
                "tower/scourge.png");
        bulletUnits.add(ball);
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
