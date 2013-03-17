package com.blogspot.javagamexyz.gamexyz.input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.blogspot.javagamexyz.gamexyz.EntityFactory;
import com.blogspot.javagamexyz.gamexyz.GameXYZ;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.Movement;
import com.blogspot.javagamexyz.gamexyz.components.PlayerSelected;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;

public class OverworldControlSystem extends EntityProcessingSystem implements InputProcessor {
	@Mapper ComponentMapper<MapPosition> pm;
	
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	
	// We need a copy of the screen implementing this controller (which has a copy of
	// the Game delegating to it) so we can change screens based on users making selections
	private GameXYZ game;
	
	private int selectedEntity;
	private Pair pathTarget;
	private State state, lastState;
	

	@SuppressWarnings("unchecked")
	public OverworldControlSystem(OrthographicCamera camera, World world, GameMap gameMap, GameXYZ game) {
		super(Aspect.getAspectForAll(PlayerSelected.class, MapPosition.class));
		
		this.camera = camera;
		this.world = world;
		this.gameMap = gameMap;
		this.game = game;
		
		state = State.DEFAULT;
		lastState = State.DEFAULT;
		selectedEntity = -1;
	}
	
	@Override
	protected void process(Entity e) {
		
		// We should only get here if the player has selected an entity and asked for a path
		if (state == State.FIND_PATH) {
			state = State.ENTITY_SELECTED;
			lastState = State.FIND_PATH;
			
			// Get the entity's position
			MapPosition pos = pm.getSafe(e);
			
			// Add a Movement component to the entity
			Movement movement = new Movement(pos.x,pos.y,pathTarget.x,pathTarget.y, gameMap);
			e.addComponent(movement);
			e.changedInWorld();
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		// Are they releasing from dragging?
		if (state == State.DRAGGING) {
			state = lastState;
			lastState = State.DRAGGING;
			return true;
		}
		
		// Otherwise, get the coordinates they clicked on
		Pair coords = MapTools.window2world(Gdx.input.getX(), Gdx.input.getY(), camera);
			
		// Check the entityID of the cell they click on
		int entityId = gameMap.getEntityAt(coords.x, coords.y);
		
		// If it's an actual entity (not empty) then "select" it (unless it's already selected)		
		if ((entityId > -1) && (entityId != selectedEntity)){
			
			// If there was previously another entity selected, "deselect" it
			if (selectedEntity > -1) {
				Entity old = world.getEntity(selectedEntity);
				old.removeComponent(PlayerSelected.class);
				old.removeComponent(Movement.class);
				old.changedInWorld();
			}
			
			// Now select the current entity
			selectedEntity = entityId;
			Entity e = world.getEntity(selectedEntity);
			e.addComponent(new PlayerSelected());
			e.changedInWorld();
			System.out.println(e.getId());
			
			EntityFactory.createClick(world, coords.x, coords.y, 0.1f, 4f).addToWorld();
			
			lastState = state;
			state = State.ENTITY_SELECTED;
			
			return true;
		}
		
		// Are they clicking to find a new path?
		else if (state == State.ENTITY_SELECTED) {
			lastState = state;
			state = State.FIND_PATH;
			pathTarget = coords;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// If it hadn't been dragging, set the current state to dragging 
		if (state != State.DRAGGING) {
			lastState = state;
			state = State.DRAGGING;
		}
		Vector2 delta = new Vector2(-camera.zoom*Gdx.input.getDeltaX(), camera.zoom*Gdx.input.getDeltaY());
		camera.translate(delta);
		
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if ((camera.zoom > 0.2f || amount == 1) && (camera.zoom < 8 || amount == -1)) camera.zoom += amount*0.1;
		return true;
	}
	
	private enum State {
		DEFAULT,
		ENTITY_SELECTED,
		DRAGGING,
		FIND_PATH,
	};
}
