package com.mygdx.game.resources.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Target PNG ->  player/warpsara-nohelmet-anim-sheet-alpha.png
 * size       ->  1008 * 576
 * grid       ->  20 row  * 12 column
 * unit       ->  48px * 48px
 */
public class PlayerResources {
    private static final Texture PLAYER_FRAME = new Texture("player/warpsara-nohelmet-anim-sheet-alpha.png");
    private static final float rowPx = 1008f;
    private static final float columnPx = 576f;
    private static final int rowGrid = 20;
    private static final int columnGrid = 12;
    private static final int unitPx = 48;
    private static final float duration = 0.1f;

    public static final Animation<TextureRegion> RUN_RIGHT = playerRunRightAnimation();
    public static final Animation<TextureRegion> RUN_LEFT = playerRunLeftAnimation();
    public static final Animation<TextureRegion> IDLE_RIGHT = playerIdleRightAnimation();
    public static final Animation<TextureRegion> IDLE_LEFT = playerIdleLeftAnimation();
    public static final Animation<TextureRegion> BULLET_HIT = bulletHitAnimation();

    private static Animation<TextureRegion> playerRunRightAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);
        //第4行第9列为目标帧,
        int targetRow = 4;
        int targetColumn = 9;

        TextureRegion[] walkFrames = new TextureRegion[targetColumn + 1];
        int index = 0;
        for (int i = 0; i < rowGrid; i++) {
            for (int j = 0; j < columnGrid; j++) {
                TextureRegion current = tmp[i][j];
                if (i == targetRow && j <= targetColumn) {
                    walkFrames[index++] = current;
                }
            }
        }
        return new Animation<>(duration, walkFrames);
    }

    private static Animation<TextureRegion> playerRunLeftAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);
        //第4行第9列为目标帧,
        int targetRow = 4;
        int targetColumn = 9;

        TextureRegion[] walkFrames = new TextureRegion[targetColumn + 1];
        int index = 0;
        for (int i = 0; i < rowGrid; i++) {
            for (int j = 0; j < columnGrid; j++) {
                TextureRegion current = tmp[i][j];
                if (i == targetRow && j <= targetColumn) {
                    current.flip(true, false);
                    walkFrames[index++] = current;
                }
            }
        }
        return new Animation<>(duration, walkFrames);
    }

    private static Animation<TextureRegion> playerIdleRightAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);
        //第4行第9列为目标帧,
        int targetRow = 1;
        int targetColumn = 3;

        TextureRegion[] walkFrames = new TextureRegion[targetColumn + 1];
        int index = 0;
        for (int i = 0; i < rowGrid; i++) {
            for (int j = 0; j < columnGrid; j++) {
                TextureRegion current = tmp[i][j];
                if (i == targetRow && j <= targetColumn) {
                    walkFrames[index++] = current;
                }
            }
        }
        return new Animation<>(duration, walkFrames);
    }

    private static Animation<TextureRegion> playerIdleLeftAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);
        //第4行第9列为目标帧,
        int targetRow = 1;
        int targetColumn = 3;

        TextureRegion[] walkFrames = new TextureRegion[targetColumn + 1];
        int index = 0;
        for (int i = 0; i < rowGrid; i++) {
            for (int j = 0; j < columnGrid; j++) {
                TextureRegion current = tmp[i][j];
                if (i == targetRow && j <= targetColumn) {
                    current.flip(true, false);
                    walkFrames[index++] = current;
                }
            }
        }
        return new Animation<>(duration, walkFrames);
    }

    private static Animation<TextureRegion> bulletHitAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);

        TextureRegion[] bulletHitFrames = new TextureRegion[3];
        int index = 0;
        bulletHitFrames[index++] = tmp[8][11];
        bulletHitFrames[index++] = tmp[7][11];
        bulletHitFrames[index++] = tmp[6][11];
        return new Animation<>(0.1f, bulletHitFrames);
    }
}
