package com.mygdx.game.main.copoment;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.main.contants.EntityEnum;
import com.mygdx.game.main.entity.BulletObj;
import com.mygdx.game.main.entity.KinematicObj;
import com.mygdx.game.main.entity.StaticObj;
import com.mygdx.game.main.entity.Player;
import com.mygdx.game.resources.ImageResources;
import com.mygdx.game.resources.StyleResources;
import com.mygdx.game.resources.WorldResources;
import com.mygdx.game.resources.player.PlayerResources;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyBox2DImgWorld extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion img;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private boolean isPressing;
    float stateTime = 0L;

    private Stage stage;
    private Touchpad touchpad;
    private Player player;
    private StaticObj box;
    private KinematicObj movingObj;
    private KinematicObj movingObj2;
    private StaticObj ground;
    Sprite sprite;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = ImageResources.BAD_LOGIC;
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 64f, Gdx.graphics.getHeight() / 64f);



        createTouchpad();

        movingObj = new KinematicObj(world, 13, 13, 1.28f, 1.28f, ImageResources.BAD_LOGIC);
        movingObj2 = new KinematicObj(world, 16, 16, 1.28f, 1.28f, ImageResources.BAD_LOGIC);
        ground = new StaticObj(world, 0, 0, Gdx.graphics.getWidth(), 10, ImageResources.BAD_LOGIC);
        box = new StaticObj(world, 10, 10 + 1.28f, 1.28f, 1.28f, ImageResources.BAD_LOGIC);
        player = new Player(world, 10, 20, 0.25f, 0.25f);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime = stateTime + Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        handleTouchEvent();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        // 给Box2D世界里的物体绘制轮廓，让我们看得更清楚，正式游戏需要注释掉这个渲染
        debugRenderer.render(world, camera.combined);

        // 更新世界里的关系 这个要放在绘制之后，最好放最后面
        world.step(1 / 60f, 6, 2);
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
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                isPressing = true;
                if (touchpad.getKnobX() > 50) {
                    player.setWalkAnimation(PlayerResources.RUN_RIGHT);
                    player.setMoveRight(true);
                    player.setMoveLeft(false);
                } else if (touchpad.getKnobX() <= 50) {
                    player.setWalkAnimation(PlayerResources.RUN_LEFT);
                    player.setMoveLeft(true);
                    player.setMoveRight(false);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressing = false;
            }
        });

        // 设置按钮的位置
        Button buttonJump = new Button(StyleResources.getButtonStyle());
        Button buttonShoot = new Button(StyleResources.getButtonStyle());
        buttonJump.setPosition(Gdx.graphics.getWidth() - 200, 250);
        buttonShoot.setPosition(Gdx.graphics.getWidth() - 350, 100);
        buttonJump.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setJump(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setJump(false);
            }
        });
        buttonShoot.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float direction = player.isMoveRight() ? 1f : -1f;
                BulletObj tempObj = new BulletObj(world,
                        player.getBody().getPosition().x + direction * 0.5f, player.getBody().getPosition().y + 0.3f, 0.1f,
                        new Vector2(direction * 3f, 5f));
                player.getBullets().add(tempObj.getBody());
                return true;
            }
        });

        stage.addActor(buttonShoot);
        stage.addActor(buttonJump);
        stage.addActor(touchpad);
    }

    private void handleTouchEvent() {
        // 将相机与批处理精灵绑定
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();

        // 将绘制与相机投影绑定 关键 关键
        TextureRegion playerAnimation = player.getWalkAnimation().getKeyFrame(stateTime, true);
        TextureRegion bulletRunAnimation = PlayerResources.BULLET_RUN.getKeyFrame(stateTime, true);
        TextureRegion bulletHitAnimation = PlayerResources.BULLET_HIT.getKeyFrame(stateTime, false);//子弹动画不循环

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(movingObj.getSurface(), movingObj.getPx() - movingObj.getWidth(), movingObj.getPy() - movingObj.getWidth(), movingObj.getWidth(), movingObj.getWidth(),
                movingObj.getWidth() * 2, movingObj.getHeight() * 2, 1f, 1f
                , MathUtils.radiansToDegrees * movingObj.getBody().getAngle());//弧度转角度
        batch.draw(sprite, movingObj2.getPx() - movingObj2.getWidth(), movingObj2.getPy() - movingObj2.getWidth(), movingObj2.getWidth(), movingObj2.getWidth(),
                movingObj2.getWidth() * 2, movingObj2.getHeight() * 2, 1f, 1f
                ,0);//弧度转角度

        batch.draw(box.getSurface(), box.getPx() - box.getWidth(), box.getPy() - box.getHeight(), box.getWidth() * 2, box.getHeight() * 2);
        batch.draw(ground.getSurface(), ground.getPx(), ground.getPy(), ground.getWidth(), ground.getHeight());
        batch.draw(playerAnimation, player.getBody().getPosition().x - 0.5f / 2, player.getBody().getPosition().y - 0.5f / 2, 0, 0, 50, 50, 0.01f, 0.01f, 0);

        for (Body ball : player.getBullets()) {
            //子弹运行动画
            batch.draw(bulletRunAnimation, ball.getPosition().x - 0.3f, ball.getPosition().y - 0.1f, 0, 0, 60, 60, 0.01f, 0.01f, 0);
            if (EntityEnum.TRASH.equals(ball.getUserData())) {
                batch.draw(bulletHitAnimation, ball.getPosition().x - 0.2f, ball.getPosition().y - 0.2f, 0, 0, 50, 50, 0.01f, 0.01f, 0);
                player.getBullets().remove(ball);
                world.destroyBody(ball);
            }
        }
        batch.end();


        // 获取五星的线速度
        Vector2 linearVelocity = player.getBody().getLinearVelocity();
        if (isPressing && player.isMoveRight() && linearVelocity.x <= 2) {
            player.getBody().applyLinearImpulse(new Vector2(1f, 0), player.getBody().getWorldCenter(), true);
        }
        if (isPressing && player.isMoveLeft() && linearVelocity.x >= -2) {
            player.getBody().applyLinearImpulse(new Vector2(-1f, 0), player.getBody().getWorldCenter(), true);
        }

        // 跳起来的逻辑，比较简单。但是时候这个演示
        if (player.isJump() && linearVelocity.y <= 4) {
            player.getBody().applyLinearImpulse(new Vector2(0, 4), player.getBody().getWorldCenter(), true);
        }
        if (linearVelocity.y == 0) {
            player.setJump(false);
        }
        if (linearVelocity.x == 0) {
            player.setWalkAnimation(player.isMoveRight() ? PlayerResources.IDLE_RIGHT : PlayerResources.IDLE_LEFT);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
