package com.mygdx.game.hero;

import com.badlogic.gdx.physics.box2d.Body;

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
    private float speedX;//角色跑步速度
    private Body body;//box2d对象
    private float stateTime;

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public HeroState getState() {
        return state;
    }

    public void setState(HeroState state) {
        setStateTime(0f);
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
