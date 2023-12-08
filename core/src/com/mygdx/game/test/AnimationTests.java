package com.mygdx.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.ResourcesUtil;
import com.mygdx.game.resources.WorldResources;

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
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        animation = ResourcesUtil.getAnimationByName("adventure/texture.atlas",
                "adventurer-bow");
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
