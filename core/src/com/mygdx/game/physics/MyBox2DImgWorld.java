package com.mygdx.game.physics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.util.BodyDefUtil;
import com.mygdx.game.util.ImageResources;
import com.mygdx.game.util.SpriteUtil;
import com.mygdx.game.util.WorldResources;

public class MyBox2DImgWorld extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion img, dog;
    private OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body dogBody;
    // 在正常像素下物体重力现象不明显，需要对纹理进行缩小100++倍才有比较明显的物理效果
    private static final float reduce = 100f;// 缩小100 倍易于观察到物理现象
    private boolean isJump;

    @Override
    public void create() {
        batch = new SpriteBatch();
        dog = ImageResources.BUCKET;
        img = ImageResources.BAD_LOGIC;
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 64f, Gdx.graphics.getHeight() / 64f);

        PolygonShape groundBox = SpriteUtil.createBox(world, 0, 0,Gdx.graphics.getWidth(), 10);

        PolygonShape badlogicBox = SpriteUtil.createBox(world, 10, 10 + 1.28f, 256.0f / 2.0f / reduce, 256f / 2f / reduce);

        BodyDef dogBodyDef = BodyDefUtil.createDynamic(10, 20);
        dogBody = world.createBody(dogBodyDef);
        PolygonShape dynamicBox = SpriteUtil.createPlayer(dogBody, 50f / 2f / reduce, 50f / 2f / reduce);

        // 上面的图形要处理掉
        groundBox.dispose();
        dynamicBox.dispose();
        badlogicBox.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 获取 物体的位置
        Vector2 position = dogBody.getPosition();

        // 将相机与批处理精灵绑定
        camera.position.set(position.x, position.y, 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 10 - 1.28f, 10, 0, 0, 256, 256, 0.01f, 0.01f, 0);
        batch.draw(dog, position.x - 50 / 2 / reduce, position.y - 50 / 2 / reduce, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                0, 0, 50, 50, // 绘制图片的一部分，这里就是全部了
                1 / reduce, 1 / reduce, // 缩小100倍
                0 // 不旋转
        );
        batch.end();

        // 获取五星的线速度
        Vector2 linearVelocity = dogBody.getLinearVelocity();
        if (Gdx.input.isKeyPressed(Input.Keys.D) && linearVelocity.x <= 2) { // 现在最大速度为 2，不然会放飞自我
            // 施加冲动 让物体运行起来，可以看成我们推一下物体就往一边移动了
            dogBody.applyLinearImpulse(new Vector2(0.1f, 0), dogBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && linearVelocity.x >= -2) {
            dogBody.applyLinearImpulse(new Vector2(-0.1f, 0), dogBody.getWorldCenter(), true);
        }

        // 跳起来的逻辑，比较简单。但是时候这个演示
        if (!isJump && Gdx.input.isKeyPressed(Input.Keys.W) && linearVelocity.y <= 4) {
            dogBody.applyLinearImpulse(new Vector2(0, 4), dogBody.getWorldCenter(), true);
            isJump = true;
        }
        if (linearVelocity.y == 0) {
            isJump = false;
        }

        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 60f, 6, 2);
    }
}
