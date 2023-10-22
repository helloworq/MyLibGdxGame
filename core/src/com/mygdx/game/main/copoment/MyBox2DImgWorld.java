package com.mygdx.game.main.copoment;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.util.ImageResources;
import com.mygdx.game.util.WorldResources;

import java.util.ArrayList;
import java.util.Random;

public class MyBox2DImgWorld extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion img;
    private OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body dogBody;
    // 在正常像素下物体重力现象不明显，需要对纹理进行缩小100++倍才有比较明显的物理效果
    private static final float reduce = 100f;// 缩小100 倍易于观察到物理现象
    private boolean isJump;
    private boolean isPressing;
    private boolean moveLeft;
    private boolean moveRight;
    // 按钮 弹起 状态的纹理
    private Texture upTexture;
    private Texture downTexture;
    private Button buttonJump;
    private Button buttonShoot;

    float stateTime = 0L;
    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)

    Stage stage;
    Touchpad touchpad;

    ShapeRenderer shape;
    Random r = new Random();
    ArrayList<Body> balls = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        img = ImageResources.BAD_LOGIC;
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        walkAnimation = ImageResources.playerAnimation();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 64f, Gdx.graphics.getHeight() / 64f);

        createTouchpad();

        createGround(0, 0, Gdx.graphics.getWidth(), 10);
        createBox(10, 10 + 1.28f, 256.0f / 2.0f / reduce, 256f / 2f / reduce);
        createMovingObj(12, 12, 256.0f / 2.0f / reduce, 256f / 2f / reduce);
        createPlayer(10, 20, 50f / 2f / reduce, 50f / 2f / reduce);
    }

    private void createTouchpad() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle(
                new TextureRegionDrawable(ImageResources.PAD),
                new TextureRegionDrawable(ImageResources.BLANK_BUTTON));
        touchpad = new Touchpad(20, touchpadStyle);
        touchpad.setBounds(100, 100, 100, 100);
        touchpad.addListener(new InputListener() {
            final Rectangle b = new Rectangle();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                isPressing = true;
                if (touchpad.getKnobX() > 50) {
                    moveRight = true;
                    moveLeft = false;
                } else if (touchpad.getKnobX() <= 50) {
                    moveRight = false;
                    moveLeft = true;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressing = false;
            }
        });

        upTexture = ImageResources.X_BUTTON;
        downTexture = ImageResources.Y_BUTTON;
        Button.ButtonStyle style = new Button.ButtonStyle();
        // 设置 style 的 弹起 和 按下 状态的纹理区域
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        buttonJump = new Button(style);
        buttonShoot = new Button(style);
        // 设置按钮的位置
        buttonJump.setPosition(Gdx.graphics.getWidth()-200, 250);
        buttonShoot.setPosition(Gdx.graphics.getWidth()-350, 100);
        // 给按钮添加点击监听器
        buttonJump.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isJump = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isJump = false;
            }
        });
        buttonShoot.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BodyDef tempBodyDef = new BodyDef();
                tempBodyDef.type = BodyDef.BodyType.DynamicBody;
                tempBodyDef.position.x = dogBody.getPosition().x + 0.1f;
                tempBodyDef.position.y = dogBody.getPosition().y;
                tempBodyDef.linearVelocity.set(20f, 10f);

                Body temp = world.createBody(tempBodyDef);
                // 再添加一个动态物体，可以把他看成玩家
                CircleShape dynamicShape = new CircleShape();
                dynamicShape.setRadius(0.1f);

                // 给物体添加一些属性
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = dynamicShape;// 形状
                fixtureDef.restitution = 0.3f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
                temp.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象

                balls.add(temp);
                return true;
            }
        });

        stage.addActor(buttonShoot);
        stage.addActor(buttonJump);
        stage.addActor(touchpad);
    }

    private void createPlayer(float px, float py, float width, float height) {
        BodyDef dogBodyDef = new BodyDef();
        dogBodyDef.type = BodyDef.BodyType.DynamicBody;
        dogBodyDef.position.x = px;
        dogBodyDef.position.y = py;

        dogBody = world.createBody(dogBodyDef);
        // 再添加一个动态物体，可以把他看成玩家
        PolygonShape dynamicShape = new PolygonShape();
        dynamicShape.setAsBox(width, height);

        // 给物体添加一些属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicShape;// 形状
        fixtureDef.restitution = 0f; // 设置这个值后，物体掉落到地面就会弹起一点高度...
        dogBody.createFixture(fixtureDef);//设置自定义数据可以从这个物体获取这个数据对象

        dynamicShape.dispose();
    }

    private void createGround(float px, float py, float width, float height) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        groundBodyDef.position.x = px;// 位置
        groundBodyDef.position.y = py;

        // 创建这个地面的身体，我们对这个物体
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();// 物体的形状，这样创建是矩形的
        groundShape.setAsBox(width, height);// 物体的宽高
        groundBody.createFixture(groundShape, 0); // 静态物体的质量应该设为0

        groundShape.dispose();
    }

    private void createMovingObj(float px, float py, float width, float height) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.KinematicBody;// 静态的质量为0
        boxBodyDef.position.x = px;// 位置
        boxBodyDef.position.y = py;
        boxBodyDef.angularVelocity = 1;

        // 创建这个地面的身体，我们对这个物体
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape boxShape = new PolygonShape();// 物体的形状，这样创建是矩形的
        boxShape.setAsBox(width, height);// 物体的宽高
        boxBody.setUserData(ImageResources.BLANK_BUTTON);
        boxBody.createFixture(boxShape, 0); // 静态物体的质量应该设为0

        boxShape.dispose();
    }

    private void createBox(float px, float py, float width, float height) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.StaticBody;// 静态的质量为0
        boxBodyDef.position.x = px;// 位置
        boxBodyDef.position.y = py;

        // 创建这个地面的身体，我们对这个物体
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape boxShape = new PolygonShape();// 物体的形状，这样创建是矩形的
        boxShape.setAsBox(width, height);// 物体的宽高
        boxBody.createFixture(boxShape, 0); // 静态物体的质量应该设为0

        boxShape.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        handleTouchEvent();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 60f, 6, 2);
    }

    private void handleTouchEvent() {
        // 获取 物体的位置
        Vector2 position = dogBody.getPosition();

        // 将相机与批处理精灵绑定
        camera.position.set(position.x, position.y, 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 10 - 1.28f, 10, 0, 0, 256, 256, 0.01f, 0.01f, 0);
        batch.draw(currentFrame, position.x - 50 / 2 / reduce, position.y - 50 / 2 / reduce, // 设置位置 减少 50/2/reduce 是为了和物体的形状重合
                0, 0, 50, 50, // 绘制图片的一部分，这里就是全部了
                1 / reduce, 1 / reduce, // 缩小100倍
                0 // 不旋转
        );
        for (Body ball : balls) {
            batch.draw(img, ball.getPosition().x - 0.1f, ball.getPosition().y - 0.1f, 0, 0, 20, 20, 0.01f, 0.01f, 0);
        }
        batch.end();


        // 获取五星的线速度
        Vector2 linearVelocity = dogBody.getLinearVelocity();

        if (isPressing && moveRight && linearVelocity.x <= 2) {
            dogBody.applyLinearImpulse(new Vector2(0.1f, 0), dogBody.getWorldCenter(), true);
        }
        if (isPressing && moveLeft && linearVelocity.x >= -2) {
            dogBody.applyLinearImpulse(new Vector2(-0.1f, 0), dogBody.getWorldCenter(), true);
        }

        // 跳起来的逻辑，比较简单。但是时候这个演示
        if (isJump && linearVelocity.y <= 4) {
            dogBody.applyLinearImpulse(new Vector2(0, 4), dogBody.getWorldCenter(), true);
            isJump = true;
        }
        if (linearVelocity.y == 0) {
            isJump = false;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
