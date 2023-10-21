package com.mygdx.game.touch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class TouchpadTest extends ApplicationAdapter {
    Stage stage;
    Touchpad touchpad;

    public void create () {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle(
                new TextureRegionDrawable(new Texture(Gdx.files.internal("touchpad/analog_stick_bg_1.png"))),
                new TextureRegionDrawable(new Texture(Gdx.files.internal("droplet.png"))));

        touchpad = new Touchpad(20, touchpadStyle);
        touchpad.setBounds(15, 15, 100, 100);
        stage.addListener(new InputListener() {
            final Rectangle b = new Rectangle();
            Vector2 p = new Vector2();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() != touchpad) {
                    // If we didn't actually touch the touchpad, set position to our touch point
                    b.set(touchpad.getX(), touchpad.getY(), touchpad.getWidth(), touchpad.getHeight());
                    b.setCenter(x, y);
                    touchpad.setBounds(b.x, b.y, b.width, b.height);

                    // Let the touchpad know to start tracking touch
                    touchpad.fire(event);
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                System.out.println(x+"  "+y);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Put the touchpad back to its original position
                touchpad.clearActions();
                touchpad.addAction(Actions.moveTo(15, 15, 0.15f));
            }
        });
        stage.addActor(touchpad);
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
        stage.dispose();
    }
}