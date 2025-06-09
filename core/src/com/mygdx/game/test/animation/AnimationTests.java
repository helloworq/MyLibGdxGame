package com.mygdx.game.test.animation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.WorldListener;
import com.mygdx.game.test.hero.AdventurePlayerResources;

public class AnimationTests extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    float stateTime = 0L;

    Animation<TextureRegion> animation;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(new MyInputProcessor(this));

        animation = AdventurePlayerResources.IDLE_WITH_SWORD_RIGHT;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime = stateTime + Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        debugRenderer.render(world, camera.combined);


        TextureRegion curPlayerFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(curPlayerFrame, 0, 0);
        batch.end();


        world.step(1 / 60f, 6, 2);
    }
}
