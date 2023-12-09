package com.mygdx.game.resources.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 所有基本单位的基类，定义最基本的属性和方法
 */
public abstract class Unit {
    private float     x;//x坐标
    private float     y;//y坐标
    private boolean   towardsLeft;//朝向左边
    private boolean   towardsRight;//朝向右边
    private boolean   floating;//是否悬浮空中
    private boolean   isAttacking;//是否在攻击状态
    private HeroState state;//角色状态

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    private float stateTime;

    public HeroState getState() {
        return state;
    }

    public void setState(HeroState state) {
        this.state = state;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isTowardsLeft() {
        return towardsLeft;
    }

    public void setTowardsLeft(boolean towardsLeft) {
        this.towardsLeft = towardsLeft;
    }

    public boolean isTowardsRight() {
        return towardsRight;
    }

    public void setTowardsRight(boolean towardsRight) {
        this.towardsRight = towardsRight;
    }

    public boolean isFloating() {
        return floating;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
