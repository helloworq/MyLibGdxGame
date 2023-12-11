package com.mygdx.game.test.hero;

import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.Map;

public class MyInputProcessor implements InputProcessor {
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static final Map<Integer, Integer> KEY_STATUS = new HashMap<>();
    private final HeroStateHandler heroStateHandler;

    static {
        KEY_STATUS.put(51, RELEASED);//w
        KEY_STATUS.put(29, RELEASED);//a
        KEY_STATUS.put(47, RELEASED);//s
        KEY_STATUS.put(32, RELEASED);//d
        KEY_STATUS.put(38, RELEASED);//j
        KEY_STATUS.put(39, RELEASED);//k
    }

    public MyInputProcessor(HeroStateHandler heroStateHandler) {
        this.heroStateHandler = heroStateHandler;
    }

    @Override
    public boolean keyDown(int keycode) {
        KEY_STATUS.put(keycode, PRESSED);
        //heroStateHandler.updateByKeyNum(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        KEY_STATUS.put(keycode, RELEASED);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
