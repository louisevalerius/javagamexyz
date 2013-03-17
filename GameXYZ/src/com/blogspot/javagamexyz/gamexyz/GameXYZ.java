package com.blogspot.javagamexyz.gamexyz;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;
import com.blogspot.javagamexyz.gamexyz.systems.ColorAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.systems.ExpiringSystem;
import com.blogspot.javagamexyz.gamexyz.systems.ScaleAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteAnimationSystem;

public class GameXYZ extends Game {

	public int WINDOW_WIDTH;
	public int WINDOW_HEIGHT;
	
	public World world;
	private SpriteBatch batch;

	public GameXYZ(int width, int height) {
		WINDOW_WIDTH = width;
		WINDOW_HEIGHT = height;
	}
	
	public void create() {
		
    	world = new World();
    	batch = new SpriteBatch();
    	
    	world.setSystem(new SpriteAnimationSystem());
    	world.setSystem(new ScaleAnimationSystem());
    	world.setSystem(new ExpiringSystem());
    	world.setSystem(new ColorAnimationSystem());
    	world.initialize(); 
    	
    	setScreen(new OverworldScreen(this, batch, world));
	}
}
