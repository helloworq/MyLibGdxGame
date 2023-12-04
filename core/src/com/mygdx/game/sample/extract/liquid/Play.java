package com.mygdx.game.sample.extract.liquid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.sample.liquid.bitfire.postprocessing.PostProcessor;
import com.mygdx.game.sample.liquid.bitfire.postprocessing.effects.Bloom;
import com.mygdx.game.sample.liquid.bitfire.utils.ShaderLoader;
import com.mygdx.game.sample.liquid.goo.goo.Assets;
import com.mygdx.game.sample.liquid.goo.goo.Gesture;

public class Play implements Screen {
    private static final float TIMESTEP = 1 / 60f;
    private static final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private PolygonSpriteBatch batch;
    private World world;

    private Body ground;
    private boolean spawn;
    private Array<Body> bodies = new Array<Body>();

    private PostProcessor post_processor;

    @Override
    public void show() {
        world = new World(new Vector2(0f, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        batch = new PolygonSpriteBatch();

        world();

        InputProcessor input_0 = new GestureDetector(new Gesture() {}) {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                spawn = true;
                return super.touchDown(x, y, pointer, button);
            }

            @Override
            public boolean touchUp(float x, float y, int pointer, int button) {
                spawn = false;
                return super.touchUp(x, y, pointer, button);
            }
        };
        Gdx.input.setInputProcessor(input_0);

        //shader
        ShaderLoader.BasePath = "shaders/";
        post_processor = new PostProcessor(false, false, true);
        Bloom bloom = new Bloom(1000, 1000);
        bloom.setBaseIntesity(0);
        bloom.setBaseSaturation(0);
        bloom.setBloomSaturation(1);
        bloom.setBloomIntesity(10);
        bloom.setBlurAmount(0);
        bloom.setThreshold(.97f);
        post_processor.addEffect(bloom);
    }

    private void world() {
        PolygonShape polygon_shape = new PolygonShape();
        BodyDef bodyDef = new BodyDef();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 2f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = .01f;

        //bottom wall
        polygon_shape.setAsBox(40, 2);
        bodyDef.position.set(0, 8);
        fixtureDef.shape = polygon_shape;
        ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);

        polygon_shape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        camera.position.set(0, 20, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        world.getBodies(bodies);

        post_processor.capture();
        batch.begin();
        for (Body body : bodies) {
            if (body.getUserData() instanceof Sprite) {
                Sprite sprite = new Sprite(Assets.manager.get(Assets.c_green));
                sprite.setAlpha(1);
                sprite.setSize(2.75f, 2.75f);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();
        post_processor.render();

        if (spawn) {
            Vector3 mouse = new Vector3();
            camera.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            Sprite sprite = new Sprite();
            CircleShape shape = new CircleShape();
            shape.setRadius(.25f);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(mouse.x, mouse.y);
            bodyDef.gravityScale = 1;

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.friction = 0f;
            fixtureDef.restitution = 0f;
            fixtureDef.density = 1f;

            ground = world.createBody(bodyDef);
            ground.createFixture(fixtureDef);
            ground.setUserData(sprite);
            shape.dispose();
        }
        // Controls
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            debugRenderer.render(world, camera.combined);
        }
        if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 1200 / 30;
        camera.viewportHeight = 800 / 30;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        post_processor.rebind();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
        post_processor.dispose();
    }
}
