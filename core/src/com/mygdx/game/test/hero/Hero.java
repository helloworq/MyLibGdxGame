package com.mygdx.game.test.hero;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.resources.GlobalConstant;

public class Hero extends Unit {
    public Hero(World world) {
        setX(100);
        setY(900);
        setStateTime(0f);
        setTowardsRight(true);
        setTowardsLeft(false);
        setAttacking(false);
        setFloating(false);
        setState(HeroState.IDLE_RIGHT);
        setSpeedX(3f);

        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.x = 5f;
        playerDef.position.y = 20f;

        Body body = world.createBody(playerDef);
        //shape定义
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.6f);
        //物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;// 形状
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.01f; // 回弹系数

        Fixture fixture = body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象

        setBody(body);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getSurface(false, new HeroStateHandler(this)),
                getBody().getPosition().x * GlobalConstant.SCALE - 25f,
                getBody().getPosition().y * GlobalConstant.SCALE - 20f
        );
    }

    private TextureRegion getSurface(boolean loop, HeroStateHandler heroStateHandler) {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
        Animation<TextureRegion> current = getState().textureRegion;

        if (current.isAnimationFinished(getStateTime())) {
            heroStateHandler.updateByAnimationComplete(true);
            setStateTime(0f);
        }
        //System.out.println("->" + r + "  " + getStateTime());
        return current.getKeyFrame(getStateTime());
    }
}
