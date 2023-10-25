package com.mygdx.game.main.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class StaticObj {
    private float px;
    private float py;
    private float width;
    private float height;
    private Body body;

    public TextureRegion getSurface() {
        return surface;
    }

    private TextureRegion surface;

    private StaticObj() {
    }

    public StaticObj(World world,
                     float px,
                     float py,
                     float width,
                     float height,
                     TextureRegion surface) {
        this.surface = surface;
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = px;// 位置
        groundBodyDef.position.y = py;

        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundShape.setAsBox(width, height);// 物体的宽高

        //物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;// 形状
        fixtureDef.friction = 0.9f;
        Fixture fixture =groundBody.createFixture(fixtureDef); // 静态物体的质量应该设为0

        Filter filter = new Filter();
        filter.categoryBits = 2;
        filter.maskBits = 4;
        fixture.setFilterData(filter);

        groundShape.dispose();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public float getPx() {
        return px;
    }

    public float getPy() {
        return py;
    }
}
