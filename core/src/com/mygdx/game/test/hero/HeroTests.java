package com.mygdx.game.test.hero;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.WorldResources;

public class HeroTests extends ApplicationAdapter {
    private SpriteBatch        batch;
    private OrthographicCamera camera;
    private World              world;
    private Box2DDebugRenderer debugRenderer;
    HeroStateHandler heroStateHandler;

    Hero hero;

    private TiledMap         map;
    private TiledMapRenderer renderer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32,32);//地图尺寸32*32
        camera.update();


        hero = new Hero();
        heroStateHandler = new HeroStateHandler(hero);
        Gdx.input.setInputProcessor(new MyInputProcessor(heroStateHandler));

        map = new TmxMapLoader().load("maps/tests/maptests2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);//单个图库像素尺寸32px
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);

        camera.update();
        renderer.setView(camera);
        renderer.render();


        batch.begin();
        batch.draw(hero.getSurface(false, heroStateHandler), hero.getX(), hero.getY());
        batch.end();


        world.step(1 / 60f, 6, 2);
    }
}
