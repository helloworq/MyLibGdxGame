package com.mygdx.game.util;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyDefUtil {

    public static BodyDef createStatic(float px, float py) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = px;// 位置
        groundBodyDef.position.y = py;

        return groundBodyDef;
    }

    public static BodyDef createDynamic(float px, float py) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = px;
        bodyDef.position.y = py;

        return bodyDef;
    }
}
