package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageResources {
    public static final TextureRegion BUCKET = new TextureRegion(new Texture("bucket.png"), 50, 50);
    public static final TextureRegion BAD_LOGIC = new TextureRegion(new Texture("badlogic.jpg"));
    public static final Texture PALYER = new Texture("characters.png");
    public static final Texture PAD = new Texture("touchpad/analog_stick_bg_1.png");
    public static final Texture BLANK_BUTTON = new Texture("touchpad/blank_button.png");
    public static final Texture X_BUTTON = new Texture("touchpad/x_button.png");
    public static final Texture Y_BUTTON = new Texture("touchpad/y_button.png");
    public static final Texture Z_BUTTON = new Texture("touchpad/z_button.png");


    public static Animation<TextureRegion> playerAnimation() {
        int FRAME_COLS = 23, FRAME_ROWS = 4;
        Texture walkSheet = PALYER;

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[3 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        //walkSheet.dispose();
        return new Animation<>(0.1f, walkFrames);
    }
}
