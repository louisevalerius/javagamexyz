package com.blogspot.javagamexyz.gamexyz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blogspot.javagamexyz.gamexyz.utils.ImagePacker;

public class Launcher extends Game {
	
	@Override
	public void create() {
		setScreen(new GameXYZ(this));
	}
	
	public static void main(String[] args) {
		ImagePacker.run();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width=GameXYZ.WINDOW_WIDTH;
		cfg.height=GameXYZ.WINDOW_HEIGHT;
		cfg.useGL20=true;
		cfg.title = "GameXYZ";
		cfg.vSyncEnabled = false;
		cfg.resizable=false;
		new LwjglApplication(new Launcher(), cfg);
	}
}
