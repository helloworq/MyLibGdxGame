package com.mygdx.game.test.hero;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.resources.player.AdventurePlayerResources;
import com.mygdx.game.test.animation.AnimationTests;

public class MyInputProcessor implements InputProcessor {

    HeroTests tests;

    public MyInputProcessor(HeroTests tests) {
        this.tests = tests;
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
        if (character == 'a') {
            tests.animation = AdventurePlayerResources.RUN_LEFT;
        } else if (character == 'd') {
            tests.animation = AdventurePlayerResources.RUN_RIGHT;
        } else if (character == 'w') {
            tests.animation = AdventurePlayerResources.JUMP_RIGHT;
        } else if (character == 's') {
            tests.animation = AdventurePlayerResources.SLIDE_RIGHT;
        } else if (character == 'j') {
            tests.animation = AdventurePlayerResources.ATTACK_DOWN_RIGHT;
        } else if (character == 'k') {
            tests.animation = AdventurePlayerResources.ATTACK_HORIZONTAL_RIGHT;
        } else if (character == 'l') {
            tests.animation = AdventurePlayerResources.ATTACK_UP_RIGHT;
        }
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
