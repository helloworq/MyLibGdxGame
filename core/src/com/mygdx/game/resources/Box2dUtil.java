package com.mygdx.game.resources;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.physics.box2d.*;

public class Box2dUtil {
    public static void buildStaticBody(MapObjects mapObjects, World world) {
        for (MapObject mapObject : mapObjects) {
            MapProperties property = mapObject.getProperties();

            float x      = property.get("x", Float.class);
            float y      = property.get("x", Float.class);
            float width  = property.get("width", Float.class);
            float height = property.get("height", Float.class);

            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
            groundBodyDef.position.x = x / GlobalConstant.SCALE;// 位置
            groundBodyDef.position.y = y / GlobalConstant.SCALE;

            // 创建这个地面的身体，我们对这个物体
            Body         bodyody   = world.createBody(groundBodyDef);
            PolygonShape bodyShape = new PolygonShape();// 物体的形状，这样创建是矩形的
            bodyShape.setAsBox(width / GlobalConstant.SCALE, height / GlobalConstant.SCALE);// 物体的宽高

            //物理属性
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = bodyShape;// 形状
            fixtureDef.friction = 0.5f;
            Fixture fixture = bodyody.createFixture(fixtureDef); // 静态物体的质量应该设为0

            //设置碰撞过滤
            Filter filter = new Filter();
            filter.categoryBits = 0x0001;
            filter.maskBits = 0x0002 | 0x0004;
            fixture.setFilterData(filter);
        }
    }
}
