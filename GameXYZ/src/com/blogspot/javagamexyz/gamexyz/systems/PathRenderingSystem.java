package com.blogspot.javagamexyz.gamexyz.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.javagamexyz.gamexyz.components.Movement;
import com.blogspot.javagamexyz.gamexyz.custom.FloatPair;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;

public class PathRenderingSystem extends EntitySystem {
	@Mapper ComponentMapper<Movement> mm;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture feet;
	
	@SuppressWarnings("unchecked")
	public PathRenderingSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Movement.class));
		this.camera = camera;
	}
	
	@SuppressWarnings("unchecked")
	public PathRenderingSystem(OrthographicCamera camera, SpriteBatch batch) {
		super(Aspect.getAspectForAll(Movement.class));
		this.camera = camera;
		this.batch = batch;
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		feet = new Texture(Gdx.files.internal("textures/misc/feet.png"));
	}
	
	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i=0; i<entities.size(); i++) {
			process(entities.get(i));
		}
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	private void process(Entity e) {
		Movement move = mm.get(e);
		if (move.path != null) {
			for (int i=0; i<move.path.getLength(); i++) {
				FloatPair coords = MapTools.world2window(move.path.getX(i), move.path.getY(i));
				batch.draw(feet, coords.x-feet.getWidth()/2, coords.y-feet.getHeight()/2);
			}
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}


	
	
	
}
