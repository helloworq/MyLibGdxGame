package com.mygdx.game.resources.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.resources.ResourcesUtil;

public class AdventurePlayerResources {
    private static final String PATH = "adventure/texture.atlas";

    public static final Animation<TextureRegion> IDLE_RIGHT              = ResourcesUtil.getAnimationByName(PATH, "adventurer-idle", false);
    public static final Animation<TextureRegion> IDLE_LEFT               = ResourcesUtil.getAnimationByName(PATH, "adventurer-idle", true);
    public static final Animation<TextureRegion> IDLE_WITH_SWORD_RIGHT   = ResourcesUtil.getAnimationByName(PATH, "adventurer-idle2", false);
    public static final Animation<TextureRegion> IDLE_WITH_SWORD_LEFT    = ResourcesUtil.getAnimationByName(PATH, "adventurer-idle2", true);
    public static final Animation<TextureRegion> RUN_RIGHT               = ResourcesUtil.getAnimationByName(PATH, "adventurer-run", false);
    public static final Animation<TextureRegion> RUN_LEFT                = ResourcesUtil.getAnimationByName(PATH, "adventurer-run", true);
    public static final Animation<TextureRegion> RUN_FAST_RIGHT          = ResourcesUtil.getAnimationByName(PATH, "adventurer-run2", false);
    public static final Animation<TextureRegion> RUN_FAST_LEFT           = ResourcesUtil.getAnimationByName(PATH, "adventurer-run2", true);
    public static final Animation<TextureRegion> RUN_WITH_SWORD_RIGHT    = ResourcesUtil.getAnimationByName(PATH, "adventurer-run3", false);
    public static final Animation<TextureRegion> RUN_WITH_SWORD_LEFT     = ResourcesUtil.getAnimationByName(PATH, "adventurer-run3", true);
    public static final Animation<TextureRegion> RUN_POUCH_RIGHT         = ResourcesUtil.getAnimationByName(PATH, "adventurer-run-punch", false);
    public static final Animation<TextureRegion> RUN_POUCH_LEFT          = ResourcesUtil.getAnimationByName(PATH, "adventurer-run-punch", true);
    public static final Animation<TextureRegion> ATTACK_UP_RIGHT         = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack1", false);
    public static final Animation<TextureRegion> ATTACK_UP_LEFT          = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack1", true);
    public static final Animation<TextureRegion> ATTACK_DOWN_RIGHT       = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack2", false);
    public static final Animation<TextureRegion> ATTACK_DOWN_LEFT        = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack2", true);
    public static final Animation<TextureRegion> ATTACK_HORIZONTAL_RIGHT = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack3", false);
    public static final Animation<TextureRegion> ATTACK_HORIZONTAL_LEFT  = ResourcesUtil.getAnimationByName(PATH, "adventurer-attack3", true);
    public static final Animation<TextureRegion> BOW_RIGHT               = ResourcesUtil.getAnimationByName(PATH, "adventurer-bow", false);
    public static final Animation<TextureRegion> BOW_LEFT                = ResourcesUtil.getAnimationByName(PATH, "adventurer-bow", true);
    public static final Animation<TextureRegion> BOW_JUMP_RIGHT          = ResourcesUtil.getAnimationByName(PATH, "adventurer-bow-jump", false);
    public static final Animation<TextureRegion> BOW_JUMP_LEFT           = ResourcesUtil.getAnimationByName(PATH, "adventurer-bow-jump", true);
    public static final Animation<TextureRegion> JUMP_RIGHT              = ResourcesUtil.getAnimationByName(PATH, "adventurer-jump", false);
    public static final Animation<TextureRegion> JUMP_LEFT               = ResourcesUtil.getAnimationByName(PATH, "adventurer-jump", true);
    public static final Animation<TextureRegion> SLIDE_RIGHT             = ResourcesUtil.getAnimationByName(PATH, "adventurer-slide", false);
    public static final Animation<TextureRegion> SLIDE_LEFT              = ResourcesUtil.getAnimationByName(PATH, "adventurer-slide", true);

}
