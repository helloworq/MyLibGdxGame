package com.mygdx.game.test.hero;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.resources.WorldResources;
import com.mygdx.game.resources.player.Hero;
import com.mygdx.game.resources.player.HeroStateHandler;

public class HeroTests extends ApplicationAdapter {
    private SpriteBatch        batch;
    private OrthographicCamera camera;
    private World              world;
    private Box2DDebugRenderer debugRenderer;
    HeroStateHandler heroStateHandler;

    Hero hero;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = WorldResources.WORLD;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        hero = new Hero();
        heroStateHandler = new HeroStateHandler(hero);
        Gdx.input.setInputProcessor(new MyInputProcessor(heroStateHandler));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);


        batch.begin();
        batch.draw(hero.getSurface( false, heroStateHandler), 0, 0);
        batch.end();


        world.step(1 / 60f, 6, 2);
    }
}
