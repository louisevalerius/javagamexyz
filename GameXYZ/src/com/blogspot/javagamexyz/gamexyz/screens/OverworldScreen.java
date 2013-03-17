package com.blogspot.javagamexyz.gamexyz.screens;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.EntityFactory;
import com.blogspot.javagamexyz.gamexyz.GameXYZ;
import com.blogspot.javagamexyz.gamexyz.input.OverworldControlSystem;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.systems.HudRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.MapRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.PathRenderingSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteRenderSystem;

public class OverworldScreen extends AbstractScreen {
	
	public static GameMap gameMap;
	private OrthographicCamera hudCam;
	
	
	public SpriteRenderSystem spriteRenderSystem;
	public HudRenderSystem hudRenderSystem;
	public MapRenderSystem mapRenderSystem;
	public PathRenderingSystem pathRenderSystem;
	
	private OverworldControlSystem overworldControlSystem;
	
	public OverworldScreen(GameXYZ game, SpriteBatch batch, World world) {
		super(game,world);

    	gameMap  = new GameMap();
    	hudCam = new OrthographicCamera();
    	
    	spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera,batch), true);
    	mapRenderSystem = world.setSystem(new MapRenderSystem(camera,batch,gameMap),true);
    	hudRenderSystem = world.setSystem(new HudRenderSystem(hudCam, batch),true);
    	pathRenderSystem = world.setSystem(new PathRenderingSystem(camera,batch),true);
    	
    	overworldControlSystem = world.setSystem(new OverworldControlSystem(camera,world,gameMap,game));
    	Gdx.input.setInputProcessor(overworldControlSystem);
    	
    	world.initialize();
    	
    	int x, y;
    	for (int i=0; i<100; i++) {
    		do {
    			x = MathUtils.random(MapTools.width()-1);
    			y = MathUtils.random(MapTools.height()-1);
    		} while (gameMap.cellOccupied(x, y));
    		EntityFactory.createNPC(world,x,y,gameMap).addToWorld();
    	}
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		mapRenderSystem.process();
		pathRenderSystem.process();
		spriteRenderSystem.process();
		hudRenderSystem.process();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		hudCam.setToOrtho(false, width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		game.world.deleteSystem(hudRenderSystem);
		game.world.deleteSystem(mapRenderSystem);
		game.world.deleteSystem(pathRenderSystem);
		game.world.deleteSystem(spriteRenderSystem);
		
	}

}
