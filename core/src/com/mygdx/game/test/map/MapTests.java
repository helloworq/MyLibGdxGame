package com.mygdx.game.test.map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.WorldListener;
import com.mygdx.game.test.hero.AdventurePlayerResources;

public class MapTests extends ApplicationAdapter {
    private SpriteBatch        batch;
    private OrthographicCamera camera;
    private World              world;
    private Box2DDebugRenderer debugRenderer;
    float stateTime = 0L;

    Animation<TextureRegion> animation;
    private TiledMap         map;
    private TiledMapRenderer renderer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10, 10);
        camera.update();

        animation = AdventurePlayerResources.IDLE_WITH_SWORD_RIGHT;


        map = new TmxMapLoader().load("maps/tests/maptests2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 64f);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime = stateTime + Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        debugRenderer.render(world, camera.combined);

        TextureRegion curPlayerFrame = animation.getKeyFrame(stateTime, true);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.begin();
        batch.draw(curPlayerFrame, 0, 0);
        batch.end();


        world.step(1 / 60f, 6, 2);
    }
}
