package com.blogspot.javagamexyz.gamexyz;


import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;
import com.blogspot.javagamexyz.gamexyz.screens.WidgetScreen;
import com.blogspot.javagamexyz.gamexyz.systems.ColorAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.systems.ExpiringSystem;
import com.blogspot.javagamexyz.gamexyz.systems.ScaleAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.textures.TextureGenerator;

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
    	
    	play();
    	//tex();
    	//widget();
    	
	}
	
	private void play() {
		setScreen(new OverworldScreen(this, batch, world));
	}
	
	@SuppressWarnings("unused")
	private void tex() {
		TextureGenerator textureGenerator = new TextureGenerator(128,128);
    	for (int i = 0; i < 10; i++) textureGenerator.generateTexture(i, false, false);
    	Gdx.app.exit();
	}
	
	@SuppressWarnings("unused")
	private void widget() {
		setScreen(new WidgetScreen(this, batch, world));
	}
	
}
