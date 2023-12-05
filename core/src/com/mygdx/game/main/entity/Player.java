package com.mygdx.game.main.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.main.contants.EntityEnum;
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

    public void moveRight(boolean isPressing) {
        setWalkAnimation(PlayerResources.RUN_RIGHT);
        setMoveRight(true);
        setMoveLeft(false);

        if (isPressing && moveRight && body.getLinearVelocity().x <= 2) {
            body.applyLinearImpulse(new Vector2(1f, 0), body.getWorldCenter(), true);
        }
    }

    public void moveLeft(boolean isPressing) {
        setWalkAnimation(PlayerResources.RUN_LEFT);
        setMoveLeft(true);
        setMoveRight(false);

        if (isPressing && moveLeft && body.getLinearVelocity().x >= -2) {
            body.applyLinearImpulse(new Vector2(-1f, 0), body.getWorldCenter(), true);
        }
    }

    public void idle(boolean isPressing) {
        if (body.getLinearVelocity().x == 0 && !isPressing) {
            setWalkAnimation(moveRight ? PlayerResources.IDLE_RIGHT : PlayerResources.IDLE_LEFT);
        }
    }

    public void jump() {
        if (body.getLinearVelocity().y <= 4) {
            body.applyLinearImpulse(new Vector2(0, 4), body.getWorldCenter(), true);
        }
    }

    public void shoot(World world) {
        float direction = moveRight ? 1f : -1f;
        BulletObj tempObj = new BulletObj(
                world,
                body.getPosition().x + direction * 0.5f,
                body.getPosition().y + 0.3f,
                0.1f,
                new Vector2(direction * 3f, 5f));
        bullets.add(tempObj.getBody());
    }

    public void draw(SpriteBatch batch, float stateTime, World world) {
        TextureRegion curPlayerFrame = walkAnimation.getKeyFrame(stateTime, true);
        TextureRegion bulletRunAnimation = PlayerResources.BULLET_RUN.getKeyFrame(stateTime, true);
        TextureRegion bulletHitAnimation = PlayerResources.BULLET_HIT.getKeyFrame(stateTime, false);//子弹动画不循环

        batch.draw(curPlayerFrame,
                this.body.getPosition().x - 0.5f / 2,
                this.body.getPosition().y - 0.5f / 2,
                0,
                0,
                50,
                50,
                0.01f,
                0.01f,
                0);

        for (Body ball : bullets) {
            //子弹运行动画
            batch.draw(bulletRunAnimation, ball.getPosition().x - 0.3f, ball.getPosition().y - 0.1f, 0, 0, 60, 60, 0.01f, 0.01f, 0);
            if (EntityEnum.TRASH.equals(ball.getUserData())) {
                batch.draw(bulletHitAnimation, ball.getPosition().x - 0.2f, ball.getPosition().y - 0.2f, 0, 0, 50, 50, 0.01f, 0.01f, 0);
                bullets.remove(ball);
                world.destroyBody(ball);
            }
        }
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
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.01f; // 回弹系数

        Fixture fixture = body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
        Filter filter = new Filter();
        filter.categoryBits = 6;
        filter.maskBits = 2;
        fixture.setFilterData(filter);

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
