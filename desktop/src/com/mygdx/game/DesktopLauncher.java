package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.animation.Animator;
import com.mygdx.game.main.copoment.MyBox2DImgWorld;
import com.mygdx.game.physics.MyBox2DWorld;
import com.mygdx.game.touch.TouchpadTest;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("My GDX Game");

		new Lwjgl3Application(new MyBox2DImgWorld(), config);
	}
}
