package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StyleResources {

    public static Button.ButtonStyle getButtonStyle() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(ImageResources.X_BUTTON));
        style.down = new TextureRegionDrawable(new TextureRegion(ImageResources.Y_BUTTON));

        return style;
    }
}
