package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.resources.HeroState;
import com.mygdx.game.constants.GlobalConstant;

public class Enemy extends Unit {
    public World world;

    public Enemy(World world) {
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
        playerDef.position.x = 12f;
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
        fixture.setUserData("enemy");

        //设置碰撞过滤
        Filter filter = new Filter();
        filter.categoryBits = 0x0016;
        filter.maskBits = 0x0001 | 0x0002 | 0X0004 | 0x0008;
        fixture.setFilterData(filter);

        setBody(body);
    }


    public void draw(SpriteBatch batch) {
        TextureRegion surface = getSurface();
        batch.draw(surface,
                getBody().getPosition().x * GlobalConstant.SCALE - surface.getRegionWidth() / 2f,
                getBody().getPosition().y * GlobalConstant.SCALE - surface.getRegionHeight() / 2f);
    }

    private TextureRegion getSurface() {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
        Animation<TextureRegion> current = HeroState.IDLE_RIGHT.textureRegion;

        return current.getKeyFrame(getStateTime());
    }
}
