package com.mygdx.game.main.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 运动物体
 */
public class KinematicObj {
    private Body body;
    private KinematicObj(){}

    public KinematicObj(World world,
                        float px,
                        float py,
                        float width,
                        float height){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.KinematicBody;// 静态的质量为0
        boxBodyDef.position.x = px;// 位置
        boxBodyDef.position.y = py;
        boxBodyDef.angularVelocity = 1;

        // 创建这个地面的身体，我们对这个物体
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape boxShape = new PolygonShape();// 物体的形状，这样创建是矩形的
        boxShape.setAsBox(width, height);// 物体的宽高
        boxBody.createFixture(boxShape, 0); // 静态物体的质量应该设为0

        boxShape.dispose();
    }
}
