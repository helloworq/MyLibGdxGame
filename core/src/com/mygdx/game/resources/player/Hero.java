package com.mygdx.game.resources.player;

public class Hero extends Unit {
    public Hero() {
        setX(0);
        setY(0);
        setTowardsRight(true);
        setTowardsLeft(false);
        setAttacking(false);
        setFloating(false);
        setSurfaceAnimation(AdventurePlayerResources.IDLE_RIGHT);
    }
}
