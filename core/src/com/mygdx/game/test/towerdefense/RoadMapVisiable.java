package com.mygdx.game.test.towerdefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.ball.Ball;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import sun.security.util.ArrayUtil;


public class RoadMapVisiable extends ApplicationAdapter {
    ShapeRenderer shape;
    int width;
    int height;
    int count = 60;
    List<Ball> balls;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        balls = new ArrayList<>();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        //List<RoadMap.Node> nodes = RoadMap.findPath();
        int widthSplit = width / RoadMap.Y;
        int heightSplit = height / RoadMap.X;

        for (int i = 0; i < RoadMap.X; i++) {
            for (int j = 0; j < RoadMap.Y; j++) {
                Ball ball = new Ball(
                        heightSplit * j,
                        widthSplit * (RoadMap.X - i),
                        30,
                        0,
                        0,
                        RoadMap.MAP[i][j] == RoadMap.RODE
                                ? Color.GREEN
                                : Color.RED
                );
                balls.add(ball);
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);


        for (Ball b : balls) {
            shape.setColor(b.color);
            shape.circle(b.x , b.y , b.size);
        }


        shape.end();
    }
}
