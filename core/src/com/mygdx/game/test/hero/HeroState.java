package com.mygdx.game.test.hero;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.resources.GlobalConstant;
import com.mygdx.game.resources.ResourcesUtil;

public enum HeroState {
    IDLE_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-idle", false)),
    IDLE_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-idle", true)),
    IDLE_WITH_SWORD_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-idle2", false)),
    IDLE_WITH_SWORD_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-idle2", true)),
    RUN_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run", false)),
    RUN_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run", true)),
    RUN_FAST_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run2", false)),
    RUN_FAST_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run2", true)),
    RUN_WITH_SWORD_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run3", false)),
    RUN_WITH_SWORD_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run3", true)),
    RUN_POUCH_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run-punch", false)),
    RUN_POUCH_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-run-punch", true)),
    ATTACK_UP_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack1", false)),
    ATTACK_UP_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack1", true)),
    ATTACK_DOWN_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack2", false)),
    ATTACK_DOWN_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack2", true)),
    ATTACK_HORIZONTAL_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack3", false)),
    ATTACK_HORIZONTAL_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-attack3", true)),
    BOW_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-bow", false)),
    BOW_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-bow", true)),
    BOW_JUMP_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-bow-jump", false)),
    BOW_JUMP_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-bow-jump", true)),
    JUMP_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-jump", false)),
    JUMP_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-jump", true)),
    SLIDE_RIGHT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-slide", false)),
    SLIDE_LEFT(ResourcesUtil.getAnimationByName(GlobalConstant.ADVENTRURE_PATH, "adventurer-slide", true));

    public Animation<TextureRegion> textureRegion;

    private HeroState(Animation<TextureRegion> textureRegion) {
        this.textureRegion = textureRegion;
    }
}
