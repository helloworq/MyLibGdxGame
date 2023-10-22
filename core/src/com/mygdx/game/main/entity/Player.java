package com.mygdx.game.main.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.ImageResources;
import com.mygdx.game.resources.player.PlayerResources;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
    private float px;
    private float py;
    private float width;
    private float height;
    private Body body;
    private Animation<TextureRegion> walkAnimation = PlayerResources.IDLE_RIGHT;
    private boolean isJump;
    private Texture surface;
    private boolean moveLeft;
    private boolean moveRight;
    private CopyOnWriteArrayList<Body> bullets = new CopyOnWriteArrayList<>();

    private Player() {
    }

    public Player(World world,
                  float px,
                  float py,
                  float width,
                  float height) {
        //body定义
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.x = px;
        playerDef.position.y = py;

        body = world.createBody(playerDef);
        //shape定义
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        //物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;// 形状
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f; // 回弹系数

        body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public float getPx() {
        return px;
    }

    public float getPy() {
        return py;
    }

    public Vector2 getPxy() {
        return new Vector2(px, py);
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public CopyOnWriteArrayList<Body> getBullets() {
        return bullets;
    }

    public void setBullets(CopyOnWriteArrayList<Body> bullets) {
        this.bullets = bullets;
    }
}
