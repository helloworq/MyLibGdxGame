package com.mygdx.game.test.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero extends Unit {
    public Hero() {
        setX(0);
        setY(0);
        setStateTime(0f);
        setTowardsRight(true);
        setTowardsLeft(false);
        setAttacking(false);
        setFloating(false);
        setState(HeroState.IDLE_RIGHT);
    }

    public TextureRegion getSurface(boolean loop, HeroStateHandler heroStateHandler) {
        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
        Animation<TextureRegion> current = getState().textureRegion;
        if (HeroState.RUN_RIGHT.equals(getState())) {
            setX(getX() + 0.4f);
        }
        if (HeroState.RUN_LEFT.equals(getState())) {
            setX(getX() - 0.4f);
        }

        if (current.isAnimationFinished(getStateTime())) {
            heroStateHandler.updateByAnimationComplete(true);
            setStateTime(0f);
        }
        return current.getKeyFrame(getStateTime());
    }
}
