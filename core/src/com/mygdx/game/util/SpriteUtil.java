package com.mygdx.game.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SpriteUtil {
    /**
     * 创建物体
     *
     * @param world
     * @param px    x坐标
     * @param py    y坐标
     */
    public static PolygonShape createBox(
            World world,
            float px,
            float py,
            float width,
            float height) {
        BodyDef groundBodyDef = BodyDefUtil.createStatic(px, py);
        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundBox.setAsBox(width, height);// 物体的宽高
        groundBody.createFixture(groundBox, 0); // 静态物体的质量应该设为0

        return groundBox;
    }

    public static PolygonShape createPlayer(Body player,
                                             float hx,
                                             float hy) {
        // 再添加一个动态物体，可以把他看成玩家
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(hx, hy);

        // 给物体添加一些属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;// 形状
        fixtureDef.restitution = 0f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        player.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象

        return dynamicBox;
    }
}
