package com.mygdx.game.physics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MyWorld extends ApplicationAdapter {

    World world;
    Box2DDebugRenderer box2DDebugRenderer;
    Body body;
    OrthographicCamera camera;

    private static final float SCENE_WIDTH = 30f; // 13 metres wide
    private static final float SCENE_HEIGHT = 10f; // 7 metres high

    @Override
    public void create() {
        // 在X轴方向上受力为0， 在Y轴方向上受到向下的重力 9.8
        world = new World(new Vector2(0.0f, -9.8f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        /** BodyDef 定义创建刚体所需要的全部数据。可以被重复使用创建不同刚体，BodyDef之后需要绑定Shape **/
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //bodyDef.linearVelocity.set(new Vector2(1f, 1f));
        bodyDef.position.set(1, SCENE_HEIGHT / 2);

        body = world.createBody(bodyDef);

        // 刚体没有任何显示，Shape主要用来显示和做碰撞检测。
        CircleShape shape = new CircleShape(); // 多边形
        shape.setRadius(0.1f);  // 半个宽度和半个高度作为参数，这样盒子就是一米宽，一米高
        // Fixture(夹具)，将形状绑定到物体上
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;  // 恢复系数，物理受到反作用力的运动情况，值越大反向运动速度越快
        body.createFixture(fixtureDef);
        shape.dispose();

        camera = new OrthographicCamera(SCENE_WIDTH, SCENE_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        createGround();
    }


    @Override
    public void render() {
        world.step(1 / 60f, 6, 2);
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            body.setType(BodyDef.BodyType.DynamicBody);
            body.setLinearVelocity(new Vector2(5f,5f));
        }

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        world.dispose();
        box2DDebugRenderer.dispose();
    }

    private void createGround() {
        float halfGroundWidth = SCENE_WIDTH;
        float halfGroundHeight = 0.5f;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(halfGroundWidth * 0.5f, halfGroundHeight);

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(halfGroundWidth * 0.5f, halfGroundHeight);

        groundBody.createFixture(groundBox, 1.0f);

        groundBox.dispose();
    }
}
