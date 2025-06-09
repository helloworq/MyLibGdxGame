package com.mygdx.game.hero;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Hero;
import com.mygdx.game.listener.HeroStateHandler;
import com.mygdx.game.listener.MyInputProcessor;
import com.mygdx.game.util.Box2dUtil;
import com.mygdx.game.resources.WorldListener;

public class HeroTests extends ApplicationAdapter {
    public static final float GRAVITY = 9.8f;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;
    private WorldListener worldListener;
    private Box2DDebugRenderer debugRenderer;
    HeroStateHandler heroStateHandler;

    Hero hero;
    Enemy enemy;

    private TiledMap map;
    private TiledMapRenderer renderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -GRAVITY), true);
        worldListener = new WorldListener(world);
        world.setContactListener(worldListener);

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 32f, Gdx.graphics.getHeight() / 32f);//地图尺寸32*32
        camera.update();


        hero = new Hero(world);
        enemy = new Enemy(world);
        heroStateHandler = new HeroStateHandler(hero);
        Gdx.input.setInputProcessor(new MyInputProcessor(heroStateHandler));

        map = new TmxMapLoader().load("maps/tests/maptests2.tmx");

        Box2dUtil.buildStaticBody(map.getLayers().get(1).getObjects(), world);

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
        hero.draw(batch);
        enemy.draw(batch);
        batch.end();

        world.step(1 / 60f, 6, 2);
        worldListener.removeBodies();
    }
}
