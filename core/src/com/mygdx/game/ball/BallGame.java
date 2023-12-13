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

public class BallGame extends ApplicationAdapter {
    ShapeRenderer shape;
    SpriteBatch batch;
    ArrayList<Ball> balls = new ArrayList<>();
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

        for (int i = 0; i < 3; i++) {
            balls.add(new Ball(
                    r.nextInt(Gdx.graphics.getWidth()),
                    r.nextInt(Gdx.graphics.getHeight()),
                    10,
                    r.nextInt(5) + 1,
                    r.nextInt(5) + 1));
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(Color.GREEN);
        for (Ball ball : balls) {
            ball.update();
            ball.draw(shape);
        }

        for (Ball ball : balls) {
            ball.draw(shape);
        }


        shape.setColor(Color.GOLD);
        for (Ball ball : balls) {

            double distance = Math.sqrt(Math.pow(ball.x - bx, 2) + Math.pow(ball.y - by, 2));
            if (distance < (ball.size + 100)) {
                shape.setColor(Color.RED);
                float d = (float) ((Math.atan2(ball.x - bx, ball.y - by)) * (180 / Math.PI));

                System.out.println(d + "  " + (-(d - 45f)) + "  " + (-d));
                building.setRotation((-(d - 45f)));
                break;
            }

//            double distance = Math.sqrt(Math.pow(100 - x, 2) + Math.pow(100 - y, 2));
//            //   if (distance < (30 + 30)) {
//            shape.setColor(Color.RED);
//
//            float d = (float) ((Math.atan2(x - bx, y - by)) * (180 / Math.PI));
//            System.out.println("->   " + d + "   " + (-(d - 45f)));
//            building.setRotation((-(d - 45f)));
        }


        shape.circle(bx, by, 100);

        batch.begin();
        building.draw(batch);
        batch.end();

        shape.end();
    }
}