package com.mygdx.game.test.hero;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.resources.player.AdventurePlayerResources;
import com.mygdx.game.resources.player.Hero;
import com.mygdx.game.resources.player.HeroState;
import com.mygdx.game.resources.player.HeroStateHandler;
import com.mygdx.game.test.animation.AnimationTests;

public class MyInputProcessor implements InputProcessor {

    HeroStateHandler heroStateHandler;

    public MyInputProcessor(HeroStateHandler heroStateHandler) {
        this.heroStateHandler = heroStateHandler;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        heroStateHandler.updateByKeyCode(character);
        return true;
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
