package com.mygdx.game.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.game.sample.liquid.goo.goo.Gesture;
import com.mygdx.game.test.towerdefense.Unit;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BallGame extends ApplicationAdapter {
    ShapeRenderer shape;
    SpriteBatch batch;
    CopyOnWriteArrayList<Ball> balls = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Ball> bullets = new CopyOnWriteArrayList<>();
    int count = 30;
    Random r = new Random();
    Sprite building;
    int bx = 250;
    int by = 200;
    int x;
    int y;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        building = new Sprite(new Texture(Gdx.files.internal("tower/sword.png")));
        building.setPosition(bx, by);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                x = screenX;
                y = Gdx.graphics.getHeight() - screenY;
                return true;
            }
        });

        for (int i = 0; i < 99; i++) {
            balls.add(new Ball(
                    r.nextInt(Gdx.graphics.getWidth()),
                    r.nextInt(Gdx.graphics.getHeight()),
                    10,
                    r.nextInt(5) + 1,
                    r.nextInt(5) + 1,
                    Color.GREEN));
        }
    }

    private void attack(float x, float y) {
        if (count <= 0) {
            Ball ball = new Ball(
                    bx,by,
                    10,
                    5,
                    5,
                    Color.BLUE);
            bullets.add(ball);
            count = 30;
        }
        count--;
    }

    @Override
    public void render() {
        System.out.println(balls.size());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (Ball ball : balls) {
            for (Ball bullet : bullets) {
                double distance = Math.sqrt(Math.pow(bullet.x - ball.x, 2) + Math.pow(bullet.y - ball.y, 2));
                if (distance < (ball.size + bullet.size)) {
                    bullets.remove(bullet);
                    balls.remove(ball);
                }
            }
        }

        for (Ball ball : balls) {
            shape.setColor(ball.color);
            ball.update();
            ball.draw(shape);
        }

        for (Ball ball : bullets) {
            shape.setColor(ball.color);
            ball.update();
            ball.draw(shape);
        }


        shape.setColor(Color.GOLD);
        for (Ball ball : balls) {
            double distance = Math.sqrt(Math.pow(ball.x - bx, 2) + Math.pow(ball.y - by, 2));
            if (distance < (ball.size + 100)) {
                shape.setColor(Color.BROWN);
                float d = (float) ((Math.atan2(ball.x - bx, ball.y - by)) * (180 / Math.PI));
                building.setRotation((-(d - 45f)));

                attack(ball.x, ball.y);
                break;
            }
        }
        shape.circle(bx, by, 100);

        batch.begin();
        building.draw(batch);
        batch.end();

        shape.end();
    }
}