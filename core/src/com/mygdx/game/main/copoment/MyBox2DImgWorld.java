package com.mygdx.game.main.copoment;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.main.entity.KinematicObj;
import com.mygdx.game.main.entity.StaticObj;
import com.mygdx.game.main.entity.Player;
import com.mygdx.game.resources.ImageResources;
import com.mygdx.game.resources.StyleResources;
import com.mygdx.game.resources.WorldResources;

public class MyBox2DImgWorld extends ApplicationAdapter {
    private SpriteBatch batch;
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
    private AssetManager assetManager;
    private TiledMap map;
    private TiledMapRenderer renderer;

    static {
        //预加载
        Box2D.init();//https://github.com/libgdx/libgdx/issues/1730
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth() / 64f, Gdx.graphics.getHeight() / 64f);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("maps/isometric_grass_and_water.tmx", TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get("maps/isometric_grass_and_water.tmx");
        renderer = new IsometricTiledMapRenderer(map, 1f / 64f);


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

        renderer.setView(camera);
        renderer.render();

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
                player.jump();
                return true;
            }
        });
        buttonShoot.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.shoot(world);
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
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        movingObj.draw(batch);
        movingObj2.draw(batch);
        box.draw(batch);
        ground.draw(batch);
        player.draw(batch, stateTime, world);

        batch.end();

        if (isPressing) {
            if (touchpad.getKnobX() > 50) {
                player.moveRight(true);
            } else if (touchpad.getKnobX() <= 50) {
                player.moveLeft(true);
            }
        } else {
            player.idle(false);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
