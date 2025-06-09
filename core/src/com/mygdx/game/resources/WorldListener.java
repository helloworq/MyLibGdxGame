package com.mygdx.game.resources;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.main.contants.EntityEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Box2D 的碰撞检测规则如下：
 * 如果两个物体的 groupIndex 相同且为正数，它们会碰撞。
 * 如果两个物体的 groupIndex 相同且为负数，它们不会碰撞。
 * 如果 groupIndex 不同，则检查 categoryBits 和 maskBits：
 * 物体 A 的 categoryBits 和物体 B 的 maskBits 进行按位与（&）操作。
 * 物体 B 的 categoryBits 和物体 A 的 maskBits 进行按位与（&）操作。
 * 如果两个结果都不为 0，则物体 A 和物体 B 会碰撞。
 */

/**
 * Filter是"过滤"的意思，所以FilterData可以简单的理解成用来过滤碰撞刚体(比如接下来哦我么要实现的矩形只与矩形刚体碰撞，圆形只与圆形刚体碰撞)。FilterData有3个属性：groupIndex、categoryBits和maskBits，他们的用法如下：
 * <p>
 * groupIndex：表示刚体的分组信息。相同groupIndex属性的刚体属性一个组，groupIndex为正数时，刚体只和同组的刚体发生碰撞。groupIndex为负数时，刚体只和同组之外的刚体进行碰撞。
 * categoryBits：表示刚体的分组信息，但不决定要碰撞的分组对象。另外，值得注意的，这个值必须是2的N次方。当然设置成其他值，程序不会报错，但是实际的碰撞分类效果，可能会出现意想不到的差错。
 * maskBits：表示刚体要碰撞的那个刚体分组对象。这个值通常是另外一个FilterData对象的categoryBits属性，表示只与该类刚体发生碰撞。如果要对多组刚体进行碰撞，可以设置maskBits为多个categoryBits的加合。如要和categoryBits分别为2和4的刚体组都进行碰撞，可以设置maskBits属性为6。
 * 举个例子，比如，圆形刚体的categoryBits和maskBits分别为2和2，矩形刚体的categoryBits和maskBits分别为4和4。那么圆形与矩形刚体之间不会发生碰撞，只有相同形状刚体之间才会发生碰撞。
 * <p>
 * filterData.categoryBits指自己所属的碰撞种类
 * filterData.maskBits指与其碰撞的种类
 * <p>
 * b2FixtureDef playerFixtureDef, monsterFixtureDef;
 * playerFixtureDef.filter.categoryBits = 0x0002;
 * monsterFixtureDef.filter.categoryBits = 0x0004;
 * playerFixtureDef.filter.maskBits = 0x0004;
 * monsterFixtureDef.filter.maskBits = 0x0002;
 * 下面代码给出了指定规则下碰撞发生的条件：
 * <p>
 * uint16 catA = fixtureA.filter.categoryBits;
 * uint16 maskA = fixtureA.filter.maskBits;
 * uint16 catB = fixtureB.filter.categoryBits;
 * uint16 maskB = fixtureB.filter.maskBits;
 * <p>
 * if ((catA & maskB) != 0 && (catB & maskA) != 0)
 * {
 * // fixtures can collide
 * }
 */
public class WorldListener implements ContactListener{
    private World world;
    private List<Body> bodiesToRemove = new ArrayList<>();


    public WorldListener(World world) {
        this.world = world;
    }

    // 在物理模拟结束后调用
    public void removeBodies() {
        for (Body body : bodiesToRemove) {
            world.destroyBody(body);
        }
        bodiesToRemove.clear();
    }

    @Override
    public void beginContact(Contact contact) {
        if (("enemy".equals(contact.getFixtureA().getUserData()) && "bullet".equals(contact.getFixtureB().getUserData())) ||
                ("bullet".equals(contact.getFixtureA().getUserData()) && "enemy".equals(contact.getFixtureB().getUserData()))) {
            bodiesToRemove.add(contact.getFixtureA().getBody());
            bodiesToRemove.add(contact.getFixtureB().getBody());
        }

        if (EntityEnum.BULLET.equals(contact.getFixtureA().getBody().getUserData())) {
            contact.getFixtureA().getBody().setUserData(EntityEnum.TRASH);
        }
        if (EntityEnum.BULLET.equals(contact.getFixtureB().getBody().getUserData())) {
            contact.getFixtureB().getBody().setUserData(EntityEnum.TRASH);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
