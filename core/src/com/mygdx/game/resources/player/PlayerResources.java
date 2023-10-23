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

    public static final Animation<TextureRegion> RUN_RIGHT = playerRunRightAnimation();
    public static final Animation<TextureRegion> RUN_LEFT = playerRunLeftAnimation();
    public static final Animation<TextureRegion> IDLE_RIGHT = playerIdleRightAnimation();
    public static final Animation<TextureRegion> IDLE_LEFT = playerIdleLeftAnimation();
    public static final Animation<TextureRegion> BULLET_HIT = bulletHitAnimation();
    public static final Animation<TextureRegion> BULLET_RUN = bulletRunAnimation();

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
        return new Animation<>(1f / (targetColumn + 1), walkFrames);
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
        return new Animation<>(1f / (targetColumn + 1), walkFrames);
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
        //帧间隔1/10代表一秒内每一帧的间隔时间，也就是每一秒播放10次，代表十帧
        return new Animation<>(1f / (targetColumn + 1), walkFrames);
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
        return new Animation<>(1f / (targetColumn + 1), walkFrames);
    }

    private static Animation<TextureRegion> bulletHitAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);

        TextureRegion[] bulletHitFrames = new TextureRegion[3];
        int index = 0;
        bulletHitFrames[index++] = tmp[8][11];
        bulletHitFrames[index++] = tmp[7][11];
        bulletHitFrames[index++] = tmp[6][11];
        return new Animation<>(1f, bulletHitFrames);
    }

    private static Animation<TextureRegion> bulletRunAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(PLAYER_FRAME, unitPx, unitPx);
        //第4行第9列为目标帧,
        int targetRow = 16;
        int targetColumn = 8;

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
        return new Animation<>(1f / (targetColumn + 1), walkFrames);
    }
}
