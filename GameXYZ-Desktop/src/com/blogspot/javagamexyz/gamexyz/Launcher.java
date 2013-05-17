package com.blogspot.javagamexyz.gamexyz;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blogspot.javagamexyz.gamexyz.utils.ImagePacker;

public class Launcher {
	
	private static final int WIDTH = 600;//600;
	private static final int HEIGHT = 400;//400;
	
	public static void main(String[] args) {
		ImagePacker.run();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width=WIDTH;
		cfg.height=HEIGHT;
		cfg.useGL20=true;
		cfg.title = "GameXYZ";
		cfg.vSyncEnabled = false;
		cfg.resizable=false;
		new LwjglApplication(new GameXYZ(WIDTH,HEIGHT), cfg);
	}
}
