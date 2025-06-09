package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.resources.HeroState;
import com.mygdx.game.listener.HeroStateHandler;
import com.mygdx.game.constants.GlobalConstant;

public class Hero extends Unit {
    public World world;

    public Hero(World world) {
        this.world = world;
        setStateTime(0f);
        setTowardsRight(true);
        setTowardsLeft(false);
        setAttacking(false);
        setFloating(false);
        setState(HeroState.IDLE_RIGHT);
        setSpeedX(1f);

        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.x = 5f;
        playerDef.position.y = 30f;

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
        fixture.setUserData("i am hero");

        //设置碰撞过滤
        Filter filter = new Filter();
        filter.categoryBits = 0x0002;
        filter.maskBits = 0x0001 | 0x0004; // 0x0004是地面，0x0008是子弹
        fixture.setFilterData(filter);

        setBody(body);
    }

    public Body createBody() {
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.x = getBody().getPosition().x;
        playerDef.position.y = getBody().getPosition().y;

        Body body = world.createBody(playerDef);
        body.setLinearVelocity(5f, 0f);
        //shape定义
        Shape shape = new CircleShape();
        shape.setRadius(0.2f);
        //物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;// 形状
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f; // 回弹系数

        Fixture fixture = body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
        fixture.setUserData("bullet");
        //设置碰撞过滤
        Filter filter = new Filter();
        filter.categoryBits = 0x0004;
        filter.maskBits = 0x0001 | 0x0002 | 0x0004; // 0x0001是地面，0x0002是英雄
        fixture.setFilterData(filter);

        return body;
    }

    public Body createBulletBody() {
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.KinematicBody;
        playerDef.position.x = getBody().getPosition().x;
        playerDef.position.y = getBody().getPosition().y;

        Body body = world.createBody(playerDef);
        body.setLinearVelocity(10f, 0f);
        //shape定义
        Shape shape = new CircleShape();
        shape.setRadius(0.2f);
        //物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;// 形状
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f; // 回弹系数

        Fixture fixture = body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
        fixture.setUserData("bullet");

        //设置碰撞过滤
        Filter filter = new Filter();
        filter.groupIndex = -1;
        filter.categoryBits = 0x0008;
        filter.maskBits = 0x0001 | 0x0004; // 0x0001是地面，0x0002是英雄
        fixture.setFilterData(filter);

        return body;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion surface = getSurface(false, new HeroStateHandler(this));
        batch.draw(  surface,
                getBody().getPosition().x * GlobalConstant.SCALE - surface.getRegionWidth() / 2f,
                getBody().getPosition().y * GlobalConstant.SCALE - surface.getRegionHeight() / 2f);
    }

    private TextureRegion getSurface(boolean loop, HeroStateHandler heroStateHandler) {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
        Animation<TextureRegion> current = getState().textureRegion;

        if (current.isAnimationFinished(getStateTime())) {
            heroStateHandler.updateByAnimationComplete(true);
            setStateTime(0f);
        }
        TextureRegion r = current.getKeyFrame(getStateTime());

        return r;
    }
}
