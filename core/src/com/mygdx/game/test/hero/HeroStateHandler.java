package com.mygdx.game.test.hero;

import com.badlogic.gdx.math.Vector2;

/**
 * 状态机-所有状态均在此处理
 * 更改状态的情况
 * - 键盘或者触屏
 * - 动画播放完成
 * - 被其他单位影响，受击或者其他事件
 */
public class HeroStateHandler {
    private final Hero hero;
    private int  count = 0;

    public HeroStateHandler(Hero hero) {
        this.hero = hero;
    }

    public void updateByKeyCode(Character keyCode) {
        update(keyCode, false, false);
    }

    public void updateByAnimationComplete(boolean animationComplete) {
        update(null, true, false);
    }

    public void updateByIsAttacked(boolean isAttacked) {
        update(null, false, true);
    }

    private void update(Character keyCode, boolean animationComplete, boolean isAttacked) {
        if (count>60) {
            hero.setStateTime(0.5f);//强制结束之前动画时需要重新累计帧时间
            count = 0;
        }
        switch (hero.getState()) {
            case IDLE_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                        hero.getBody().setLinearVelocity(-hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                        hero.getBody().setLinearVelocity(hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_RIGHT);
                        hero.createBulletBody();
                    }
                    else if ('k' == keyCode) {
                        hero.createBody();
                    } else break;//强制结束之前动画时需要重新累计帧时间
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case IDLE_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                        hero.getBody().setLinearVelocity(-hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                        hero.getBody().setLinearVelocity(hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case RUN_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                        hero.getBody().setLinearVelocity(-hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                        hero.getBody().setLinearVelocity(hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case RUN_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                        hero.getBody().setLinearVelocity(-hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                        hero.getBody().setLinearVelocity(hero.getSpeedX(), hero.getBody().getLinearVelocity().y);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_RIGHT);
                    } else break;//强制结束之前动画时需要重新累计帧时间
                }
                count = count + 1;
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case SLIDE_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case SLIDE_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_RIGHT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case ATTACK_HORIZONTAL_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    hero.setStateTime(0f);
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_DOWN_RIGHT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case ATTACK_HORIZONTAL_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_DOWN_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case ATTACK_DOWN_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    hero.setStateTime(0f);
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_UP_RIGHT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case ATTACK_DOWN_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_UP_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case ATTACK_UP_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_RIGHT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case ATTACK_UP_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
            case JUMP_RIGHT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_RIGHT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_RIGHT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_RIGHT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_RIGHT);
                }
            }
            break;
            case JUMP_LEFT: {
                if (null != keyCode) {
                    //如果是键盘事件
                    if ('w' == keyCode) {
                        hero.setState(HeroState.JUMP_LEFT);
                        hero.getBody().applyLinearImpulse(new Vector2(0f, 5f), hero.getBody().getWorldCenter(), true);
                    } else if ('a' == keyCode) {
                        hero.setState(HeroState.RUN_LEFT);
                    } else if ('s' == keyCode) {
                        hero.setState(HeroState.SLIDE_LEFT);
                    } else if ('d' == keyCode) {
                        hero.setState(HeroState.RUN_RIGHT);
                    } else if ('j' == keyCode) {
                        hero.setState(HeroState.ATTACK_HORIZONTAL_LEFT);
                    } else break;
                }
                if (animationComplete) {
                    hero.setState(HeroState.IDLE_LEFT);
                }
            }
            break;
        }
    }
}
