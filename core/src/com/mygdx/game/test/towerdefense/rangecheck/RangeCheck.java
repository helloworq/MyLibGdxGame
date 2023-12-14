package com.mygdx.game.test.towerdefense.rangecheck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class RangeCheck extends ApplicationAdapter {
    ShapeRenderer   shape;
    ArrayList<Unit> balls = new ArrayList<>();
    Random          r     = new Random();

    @Override
    public void create() {
        shape = new ShapeRenderer();
        for (int i = 0; i < 100; i++) {
            balls.add(new Unit(
                    r.nextInt(Gdx.graphics.getWidth()),
                    r.nextInt(Gdx.graphics.getHeight()),
                    r.nextInt(10) + 10));
        }

        for (Unit ball : balls) {
            ball.color = Color.GREEN;
            for (Unit ball2 : balls) {
                if (ball.equals(ball2)) {
                    continue;
                }
                double distance = Math.sqrt(Math.pow(ball2.x - ball.x, 2) + Math.pow(ball2.y - ball.y, 2));
                if (distance < (ball.size + ball2.size)) {
                    ball.color = Color.RED;
                    break;
                }
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (Unit ball : balls) {
            shape.setColor(ball.color);
            shape.circle(ball.x, ball.y, ball.size);
        }

        shape.end();
    }
}
