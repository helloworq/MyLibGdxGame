package com.mygdx.game.sample.extract.liquid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.sample.liquid.goo.goo.Assets;

public class GameMain extends Game {

    public static Assets assets;

    @Override
    public void create() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                assets = new Assets();
                assets.load();
                Assets.manager.finishLoading();
                setScreen(new Play());
            }
        }, 2f);
    }
}
