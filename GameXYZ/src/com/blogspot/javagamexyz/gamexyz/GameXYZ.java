package com.blogspot.javagamexyz.gamexyz;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.systems.HudRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.MapRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.PlayerInputSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteAnimationSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteRenderSystem;
import com.blogspot.javagamexyz.gamexyz.utils.MapTools;

public class GameXYZ implements Screen {

	public static int WINDOW_WIDTH = 1300;
	public static int WINDOW_HEIGHT = 720;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	World world;
	Game game;
	
	private SpriteRenderSystem spriteRenderSystem;
	private HudRenderSystem hudRenderSystem;
	private MapRenderSystem mapRenderSystem;

	public GameXYZ(Game game) {
		this.game = game;
		
    	batch = new SpriteBatch();
    	camera = new OrthographicCamera();
    	
    	world = new World();
    	spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
    	mapRenderSystem = world.setSystem(new MapRenderSystem(camera),true);
    	hudRenderSystem = world.setSystem(new HudRenderSystem(camera),true);
    	
    	world.setSystem(new SpriteAnimationSystem());
    	world.setSystem(new PlayerInputSystem(camera));
    	world.initialize(); 
    	
    	EntityFactory.createMap(world).addToWorld();
    	EntityFactory.createPlayer(world, 11,14).addToWorld();
    	for (int i=0; i<100; i++) EntityFactory.createWarrior(world, MathUtils.random(MapTools.width()-1), MathUtils.random(MapTools.height()-1)).addToWorld();

	}
	
	@Override
    public void render (float delta) {
    	
    	Gdx.gl.glClearColor(0,0,0,1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	camera.update();
    	
    	world.setDelta(delta);
        world.process();
        
        mapRenderSystem.process();
        spriteRenderSystem.process();
        hudRenderSystem.process();
	}

	@Override
    public void resize (int width, int height) {
    	WINDOW_WIDTH = width;
    	WINDOW_HEIGHT = height;
    	
    	camera.setToOrtho(false, width,height);
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }
    
    @Override
    public void dispose () {
    }

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}
}