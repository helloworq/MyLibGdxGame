package com.mygdx.game.main.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicObj {
    private Body body;

    private DynamicObj() {
    }

    public DynamicObj(World world,
                      float px,
                      float py,
                      float radius,
                      Vector2 initVelocity) {
        BodyDef tempBodyDef = new BodyDef();
        tempBodyDef.type = BodyDef.BodyType.DynamicBody;
        tempBodyDef.position.x = px;
        tempBodyDef.position.y = py;
        tempBodyDef.linearVelocity.set(initVelocity);

        body = world.createBody(tempBodyDef);
        // 再添加一个动态物体，可以把他看成玩家
        CircleShape dynamicShape = new CircleShape();
        dynamicShape.setRadius(radius);

        // 给物体添加一些属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicShape;// 形状
        fixtureDef.restitution = 0.3f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
    }

    public DynamicObj(World world,
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
        fixtureDef.restitution = 0f; // 回弹系数

        body.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }
}
