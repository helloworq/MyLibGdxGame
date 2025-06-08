package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.test.hero.HeroTests;
import com.mygdx.game.test.towerdefense.BloodTester;
import com.mygdx.game.test.towerdefense.RoadMapVisiable;
import com.mygdx.game.test.towerdefense.mouseenvent.MouseEvent;
import com.mygdx.game.test.towerdefense.mouseenvent.MouseEvent2;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("My GDX Game");
		config.setWindowedMode(650,500);

		new Lwjgl3Application(new MouseEvent2(), config);
	}
}
