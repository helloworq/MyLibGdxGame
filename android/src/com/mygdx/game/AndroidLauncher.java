package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.advance.Drop;
import com.mygdx.game.main.copoment.MyBox2DImgWorld;
import com.mygdx.game.physics.B2dModel;
import com.mygdx.game.test.towerdefense.RoadMapVisiable;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new RoadMapVisiable(), config);
	}
}
