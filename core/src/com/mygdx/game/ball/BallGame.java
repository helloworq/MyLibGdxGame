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
    float                      fixDeg  = 45f;//普通图片有45°的倾角，在此进行补足
    ShapeRenderer              shape;
    SpriteBatch                batch;
    CopyOnWriteArrayList<Ball> balls   = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Ball> bullets = new CopyOnWriteArrayList<>();
    int                        count   = 10;
    Random                     r       = new Random();
    Sprite                     building;
    int                        bx      = 250;
    int                        by      = 200;
    int                        x;
    int                        y;

    private static double sin(double angle){
        return Math.sin(angle / 180 * Math.PI);
    }

    private static double cos(double angle){
        return Math.cos(angle / 180 * Math.PI);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        building = new Sprite(new Texture(Gdx.files.internal("tower/sword.png")));
        building.setPosition(bx, by);
        building.setOriginCenter();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                x = screenX;
                y = Gdx.graphics.getHeight() - screenY;
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                attack(bx,by,building.getRotation());
                return true;
            }
        });

        for (int i = 0; i < 1; i++) {
            balls.add(new Ball(
                    r.nextInt(Gdx.graphics.getWidth()),
                    r.nextInt(Gdx.graphics.getHeight()),
                    10,
                    r.nextInt(5) + 1f,
                    r.nextInt(5) + 1f,
                    Color.GREEN));
        }
    }

    private void attack(float x, float y, float deg) {
        if (count <= 0) {
            Ball ball = new Ball(
                    bx, by,
                    10,
                    (5f * cos(deg + fixDeg)),
                    (5f * sin(deg + fixDeg)),
                    Color.BLUE);
            bullets.add(ball);
            count = 10;
        }
        count--;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Line);

//        for (Ball ball : balls) {
//            for (Ball bullet : bullets) {
//                double distance = Math.sqrt(Math.pow(bullet.x - ball.x, 2) + Math.pow(bullet.y - ball.y, 2));
//                if (distance < (ball.size + bullet.size)) {
//                    bullets.remove(bullet);
//                    balls.remove(ball);
//                }
//            }
//        }

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
            double distance = Math.sqrt(Math.pow(x - bx, 2) + Math.pow(y - by, 2));
            if (distance < (ball.size + 100)) {
                shape.setColor(Color.BROWN);
                float d = (float) ((Math.atan2(y - by, x - bx)) * (180 / Math.PI));
                building.setRotation(d - fixDeg);

                //attack(bx, by, d - 45f);
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